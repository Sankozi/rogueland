package org.sankozi.rogueland.control;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sankozi.rogueland.model.*;
import org.sankozi.rogueland.model.coords.Coords;
import org.sankozi.rogueland.model.coords.Direction;

/**
 * Utilities used in game loop
 *
 * @author sankozi
 */
class GameUtils {
    private final static Logger LOG = LogManager.getLogger(GameUtils.class);

    static WeaponAttack getWeaponAttack(Actor actor, Move move) {
        WeaponAttack weaponAttack;
        Direction weaponDirection = actor.getWeaponDirection();
        if(move instanceof Move.Rotate){ //rotation is always swing
            if(move == Move.Rotate.CLOCKWISE){
                weaponAttack = WeaponAttack.get(weaponDirection.nextClockwise().nextClockwise(),
                                    WeaponAttack.WeaponMove.SWING_CLOCKWISE);
            } else {
                weaponAttack = WeaponAttack.get(weaponDirection.prevClockwise().prevClockwise(),
                                    WeaponAttack.WeaponMove.SWING_COUNTERCLOCKWISE);
            }
        } else { //move can be swing or thrust depending on angle
            Direction moveDirection = ((Move.Go)move).direction;
            if(weaponDirection == moveDirection
                    || weaponDirection == moveDirection.prevClockwise()
                    || weaponDirection == moveDirection.nextClockwise()) {
                weaponAttack = WeaponAttack.get(moveDirection, WeaponAttack.WeaponMove.THRUST);
            } else {
                weaponAttack = WeaponAttack.get(moveDirection, weaponDirection.isOnRightSideOf(moveDirection)
                                ? WeaponAttack.WeaponMove.SWING_COUNTERCLOCKWISE
                                : WeaponAttack.WeaponMove.SWING_CLOCKWISE);
            }
        }
        return weaponAttack;
    }

    static Coords getTargetLocationAndRotate(Actor a, Move m, Coords location) {
        if(m instanceof Move.Go){
			int newX = location.x;
			int newY = location.y;
            switch ((Move.Go) m) {
                case EAST:
                    newX++;
                    break;
                case NORTH:
                    newY--;
                    break;
                case WEST:
                    newX--;
                    break;
                case SOUTH:
                    newY++;
                    break;
                case NORTHEAST:
                    newY--;
                    newX++;
                    break;
                case NORTHWEST:
                    newY--;
                    newX--;
                    break;
                case SOUTHEAST:
                    newY++;
                    newX++;
                    break;
                case SOUTHWEST:
                    newY++;
                    newX--;
                    break;
                default:
                    LOG.error("unhandled move : " + m);
            }
			return new Coords(newX, newY);
        } else if(m instanceof Move.Rotate) {
			Player p = (Player) a;
			Direction dir = p.getWeaponDirection();
			switch ((Move.Rotate) m) {
				case CLOCKWISE:
					p.setWeaponDirection(dir.nextClockwise());
					break;
				case COUNTERCLOCKWISE:
					p.setWeaponDirection(dir.prevClockwise());
					break;
			}
			return location;
		} else if(m == Move.WAIT) {
			return location;
        } else {
            LOG.error("unhandled move : " + m);
			return location;
        }
    }

    static boolean validLocation(Coords playerLocation, Tile[][] tiles) {
        //location outside level
        if(playerLocation.x < 0 || playerLocation.y < 0
                || playerLocation.x >= tiles.length
                || playerLocation.y >= tiles[0].length) {
            return false;
        } else {
            //location inaccessible and that cannot be intercarted with
            Tile tile = tiles[playerLocation.x][playerLocation.y];
            return tile.type != Tile.Type.ROCK;
        }
    }

    /**
     * Default attack handle
     * @param actor
     * @param target
     */
    static void attackWithWeapon(Actor actor, Actor target, Move move){
        WeaponAttack weaponAttack = getWeaponAttack(actor, move);

        Iterable<Description> descs = target.getEffectManager().registerEffects(actor.getWeaponEffects(weaponAttack));

        switch(weaponAttack.getMove()){
            case THRUST:
                GameLog.info(actor.getObjectName() + " thrusts his weapon into "  + target.getObjectName() + ":");
                break;
            case SWING_COUNTERCLOCKWISE:
            case SWING_CLOCKWISE:
                GameLog.info(actor.getObjectName() + " swings his weapon at "  + target.getObjectName() + ":");
        }
        for(Description desc : descs) {
            GameLog.info(" " + desc.getAsString());
        }
    }

    static void processRegeneration(Actor actor){
        actor.heal(actor.destroyableParam(Destroyable.Param.DURABILITY_REGEN));
        actor.setActorParam(Actor.Param.BALANCE,
                Math.min(actor.actorParam(Actor.Param.BALANCE) + actor.actorParam(Actor.Param.BALANCE_REGEN),
                        actor.actorParam(Actor.Param.MAX_BALANCE)));
    }
}
