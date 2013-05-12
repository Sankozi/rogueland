package org.sankozi.rogueland.control;

import com.google.common.collect.ImmutableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sankozi.rogueland.generator.LevelGenerator;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;

import java.util.Collections;
import java.util.List;

import org.sankozi.rogueland.model.*;
import org.sankozi.rogueland.model.coords.Coords;
import org.sankozi.rogueland.model.coords.Dim;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Object responsible for single game instance
 * @author sankozi
 */
public class Game {
    private final static Logger LOG = LogManager.getLogger(Game.class);

    private final Provider<Player> playerProvider;

    private Level level = new Level(300, 200);
    private GameLog log = new GameLog();
    private Player player = null;
    private List<Actor> actors = Lists.newArrayList();
	private List<Actor> toBeRemoved = Lists.newArrayList();

    private List<Observer> observers = Collections.emptyList();

	private final Locator locator;
    private final LevelControl control;

    private GameRunnable runningGame;

    {
		LevelGenerator.generate(level);
        GameLevelLocator gll = new GameLevelLocator(this, level);
        locator = gll;
        control = gll;
        observers = ImmutableList.<Observer>of(new EnemySpawner());
    }

    @Inject
    public Game(Provider<Player> playerProvider) {
        this.playerProvider = playerProvider;
    }

    public Runnable provideRunnable(){
        if(runningGame == null){
			LOG.info("providing GameRunnable");
            runningGame = new GameRunnable();
            return runningGame;
        } else {
            throw new IllegalArgumentException("game has already started");
        }
    }

    /**
     * Sets controls for Player character
     * @param controls
     */
    public void setControls(Controls controls){
        this.player = playerProvider.get();
        player.setControls(controls);
        player.setLocation(new Coords(5,5));
        actors.add(player);
        Actor ai = new AiActor();
        ai.setLocation(new Coords(10,10));
        actors.add(ai);
    }

    public void addLogListener(LogListener logListener){
        log.addListener(logListener);
    }

    void addActor(Actor actor, Coords coords){
        actors.add(actor);
        Tile tile = level.getTile(coords);
        tile.actor = actor;
        actor.setLocation(coords);
    }

    private class GameRunnable implements Runnable {
        @Override
        public void run() {
            GameLog.initThreadLog(log);
            GameLog.info("Game has started");
			try {
                for(Observer observer: observers){
                    observer.attach(locator, control);
                }
				do {
					processActors();
                    for(Observer observer: observers){
                        observer.tick(locator, control);
                    }
				} while (!player.isDestroyed());
			} catch (IllegalStateException ex){
				if(ex.getCause() instanceof InterruptedException){
					LOG.info("game ended");
				}
			}
        }

        private void processPush(Actor actor) {
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

        private void push(Actor actor, int dx, int dy, int length){
            checkArgument(dx != 0 || dy != 0, "dx or dy  must other than 0");
            LOG.info("pushing");
            Coords prevLocation = actor.getLocation();
            int x = prevLocation.x;
            int y = prevLocation.y;
            Dim dim = getLevel().getDim();
            boolean locationChanged = false;
            for(int fieldsMoved = 0; fieldsMoved < length; ++fieldsMoved){
                x += dx;
                y += dy;
                if(!dim.containsCoordinates(x, y)){
                    LOG.info("pushing out of playing field");
                    break;
                } else if(!getLevel().getTiles()[x][y].isPassable()){
                    LOG.info("pushing on impassable tile ({},{})", x, y);
                    break;
                }
                LOG.info("pushing onto ({},{})", x, y);
                locationChanged = true;
            }

            if(locationChanged) {
                changeActorLocation(actor, new Coords(x, y));
            }
        }

        /**
         * Unchecked changing location of actor
         * @param actor
         * @param toCoords
         */
        private void changeActorLocation(Actor actor, Coords toCoords){
            Tile from = getLevel().getTile(actor.getLocation());
            Tile to = getLevel().getTile(toCoords);
            assert from.actor == actor : "from.actor = " + from.actor + " actor = " + actor;
            assert to.actor == null;
            to.actor = from.actor;
            from.actor = null;
            actor.setLocation(toCoords);
        }

        /**
         * Processes stats that make actor miss his turn
         * @param actor actor to be checked
         * @return true if actor will miss his turn
         */
        private boolean processTurnMissStats(Actor actor){
            float stumble = actor.actorParam(Actor.Param.STUMBLE);
            if(LOG.isDebugEnabled()){
                if(stumble > 0){
                    LOG.info("actor {} stumble {}", actor, stumble);
                }
            }
            if(stumble > 1){
                actor.setActorParam(Actor.Param.STUMBLE, 1);
            } else {
                actor.setActorParam(Actor.Param.STUMBLE, 0);
            }
            return stumble >= 1;
        }

		/**
		 * Process actor that is moving with its weapon (on different tile)
		 * @param actor 
		 */
		private void processActor(Actor actor) {
            GameUtils.processRegeneration(actor);
            boolean missingTurn = processTurnMissStats(actor);
            if(missingTurn){
                return;
            }
            Move m;
            Coords targetLocation;
            Coords actorLocation = actor.getLocation();
			Coords prevWeaponLocation = actor.getWeaponLocation();
            final Tile[][] tiles = level.getTiles();
            do {
                tiles[actorLocation.x][actorLocation.y].actor = actor;
                m = actor.act(level, locator);
                targetLocation = GameUtils.getTargetLocationAndRotate(actor, m, actorLocation);
//                LOG.info("actor : " + actor + " move : " + newLocation);
            } while (!GameUtils.validLocation(targetLocation, tiles));

			if(!targetLocation.equals(actorLocation)){
                tiles[actorLocation.x][actorLocation.y].actor = null;
				Tile tile = tiles[targetLocation.x][targetLocation.y];
				if(tile.actor != null){
					attack(actor, tile.actor);
				} else {
					actorLocation = targetLocation;
				}
				assert tiles[actorLocation.x][actorLocation.y].actor == null 
						: tiles[actorLocation.x][actorLocation.y].actor.getObjectName() + " on point " + actorLocation;
				tiles[actorLocation.x][actorLocation.y].actor = actor;
				actor.setLocation(actorLocation);
			} 
			if(actor.isArmed()){
				Coords nextWeaponLocation = actor.getWeaponLocation();

                if(prevWeaponLocation == null) {
                    tiles[nextWeaponLocation.x][nextWeaponLocation.y].weapon = true;
                    Tile tile = tiles[nextWeaponLocation.x][nextWeaponLocation.y];
                    checkWeaponAttackOnTile(actor, m, tile);
                } else if(!prevWeaponLocation.equals(nextWeaponLocation)){
//					LOG.debug("new weapon location : " + nextWeaponLocation.x + "," + nextWeaponLocation.y);
                    if(level.getDim().containsCoordinates(nextWeaponLocation)){
                        Tile tile = tiles[nextWeaponLocation.x][nextWeaponLocation.y];
                        tiles[nextWeaponLocation.x][nextWeaponLocation.y].weapon = true;
                        checkWeaponAttackOnTile(actor, m, tile);
                    }
                    if(level.getDim().containsCoordinates(prevWeaponLocation)){
					    tiles[prevWeaponLocation.x][prevWeaponLocation.y].weapon = false;
                    }
				}
			}
        }

        private void checkWeaponAttackOnTile(Actor actor, Move m, Tile tile) {
            Actor targetActor = tile.actor;
            if(targetActor != null){
                GameUtils.attackWithWeapon(actor, targetActor, m); //this method can change tile.actor
                if(targetActor.isDestroyed()){
                    removeActor(targetActor);
                } else {
                    processPush(targetActor);
                }
            }
        }

        private void processActors() {
            for(Actor actor:actors){
				if(!actor.isDestroyed()){
					processActor(actor);
				}
            }
			actors.removeAll(toBeRemoved);
			toBeRemoved.clear();
        }
    }

    private void removeActor(Actor actor){
		if(actor == player){
			getLog().log("You die...", MessageType.INFO);
		}
		toBeRemoved.add(actor);
        level.getTiles()[actor.getLocation().x][actor.getLocation().y ].actor = null;
    }

    /**
     * Default attack handle
     * @param actor
     * @param target
     */
    private void attack(Actor actor, Actor target){
        //TODO knockback for bump attack
        Description desc = target.getEffectManager().registerEffect(actor.getBumpEffect());
        GameLog.info(actor.getObjectName() + " is attacking " + target.getObjectName() + ":");
        GameLog.info(" " + desc.getAsString());
        if(target.isDestroyed()){
            removeActor(target);
        }
    }

    /**
	 * Returns true if this game object has been initialized and represents running
	 * game
	 * @return 
	 */
	public boolean isInitialized(){
		return player != null;
	}

	public Level getLevel() {
        return level;
    }

    public GameLog getLog() {
        return log;
    }

    public Player getPlayer() {
        return player;
    }
}
