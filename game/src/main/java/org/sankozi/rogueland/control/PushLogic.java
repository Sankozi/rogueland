package org.sankozi.rogueland.control;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sankozi.rogueland.model.Actor;
import org.sankozi.rogueland.model.Level;
import org.sankozi.rogueland.model.coords.Coords;
import org.sankozi.rogueland.model.coords.Dim;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Class responsible for Pushing mechanic
 *
 * @author sankozi
 */
final class PushLogic {
    private final static Logger LOG = LogManager.getLogger(PushLogic.class);

    private final Level level;

    PushLogic(Level level) {
        this.level = level;
    }

    void processPush(Actor actor) {
        if(actor.actorParam(Actor.Param.OFF_BALANCE) > 0){
            LOG.info("{} is off balance", actor.getName());
            float hPush = actor.actorParam(Actor.Param.PUSH_HORIZONTAL);
            float vPush = actor.actorParam(Actor.Param.PUSH_VERTICAL);
            float balance = actor.actorParam(Actor.Param.BALANCE);
            float maxBalance = actor.actorParam(Actor.Param.MAX_BALANCE);

            float value = Math.max(Math.abs(hPush), Math.abs(vPush));

            actor.setActorParam(Actor.Param.PUSH_HORIZONTAL, 0f);
            actor.setActorParam(Actor.Param.PUSH_VERTICAL, 0f);
            actor.setActorParam(Actor.Param.OFF_BALANCE, 0f);

            LOG.info("push force = {}", value);

            if(balance > value){ //some balance left
                balance -= value;
                actor.setActorParam(Actor.Param.BALANCE, balance);
                float ratio = value / maxBalance;

                LOG.info("{} reducing balance by {}", actor.getName(), value);

                if(ratio > 0.333) { //minimal push
                    push(actor, (int) Math.signum(hPush), (int) Math.signum(vPush), 1);
                    if(ratio > 0.666){ //push with sumble
                        actor.changeActorParam(Actor.Param.STUMBLE, +1);
                    }
                }
            } else { //push with big stumble
                actor.setActorParam(Actor.Param.BALANCE, 0f);
                LOG.info("{} balance is 0", actor.getName());
                float reducedHPush = Math.signum(hPush) * Math.max(Math.abs(hPush) - value, 0);
                float reducedVPush = Math.signum(vPush) * Math.max(Math.abs(vPush) - value, 0);
                push(actor,(int) Math.signum(hPush), (int) Math.signum(vPush),
                        1 + (int) (10f * Math.max(Math.abs(reducedHPush), Math.abs(reducedVPush)) / maxBalance));
                actor.changeActorParam(Actor.Param.STUMBLE, +2);
            }
        }
    }

    void push(Actor actor, int dx, int dy, int length){
        checkArgument(dx != 0 || dy != 0, "dx or dy  must other than 0");
        Coords prevLocation = actor.getLocation();
        int x = prevLocation.x;
        int y = prevLocation.y;
        Dim dim = level.getDim();
        boolean locationChanged = false;
        for(int fieldsMoved = 0; fieldsMoved < length; ++fieldsMoved){
            x += dx;
            y += dy;
            if(!dim.containsCoordinates(x, y)){
                LOG.info("pushing out of playing field");
                break;
            } else if(!level.getTiles()[x][y].isPassable()){
                LOG.info("pushing on impassable tile ({},{})", x, y);
                break;
            }
            LOG.info("pushing onto ({},{})", x, y);
            locationChanged = true;
        }

        if(locationChanged) {
            level.changeActorLocation(actor, new Coords(x, y));
        }
    }
}
