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
import org.sankozi.rogueland.model.Destroyable.Param;
import org.sankozi.rogueland.model.*;
import org.sankozi.rogueland.model.coords.Coords;
import org.sankozi.rogueland.model.coords.Direction;

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

        private void processRegeneration(Actor actor){
            actor.heal(actor.destroyableParam(Param.DURABILITY_REGEN));
            actor.setActorParam(Actor.Param.BALANCE,
                    Math.min(actor.actorParam(Actor.Param.BALANCE) + actor.actorParam(Actor.Param.BALANCE_REGEN),
                             actor.actorParam(Actor.Param.MAX_BALANCE)));
        }

		/**
		 * Process actor that is moving with its weapon (on different tile)
		 * @param actor 
		 */
		private void processActor(Actor actor) {
            processRegeneration(actor);
            Move m;
            Coords targetLocation;
            Coords actorLocation = actor.getLocation();
			Coords prevWeaponLocation = actor.getWeaponLocation();
            final Tile[][] tiles = level.getTiles();
            do {
                tiles[actorLocation.x][actorLocation.y].actor = actor;
                m = actor.act(level, locator);
                targetLocation = getTargetLocationAndRotate(actor, m, actorLocation);
//                LOG.info("actor : " + actor + " move : " + newLocation);
            } while (!validLocation(targetLocation, tiles));

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
                    if(tile.actor != null){
                        attackWithWeapon(actor, tile.actor, m);
                    }
                } else if(!prevWeaponLocation.equals(nextWeaponLocation)){
					LOG.debug("new weapon location : " + nextWeaponLocation.x + "," + nextWeaponLocation.y);
                    if(level.getDim().containsCoordinates(nextWeaponLocation)){
                        Tile tile = tiles[nextWeaponLocation.x][nextWeaponLocation.y];
                        tiles[nextWeaponLocation.x][nextWeaponLocation.y].weapon = true;
                        if(tile.actor != null){
                            attackWithWeapon(actor, tile.actor, m);
                        }
                    }
                    if(level.getDim().containsCoordinates(prevWeaponLocation)){
					    tiles[prevWeaponLocation.x][prevWeaponLocation.y].weapon = false;
                    }
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
        Description desc = target.getEffectManager().registerEffect(actor.getBumpEffect());
        GameLog.info(actor.getObjectName() + " is attacking " + target.getObjectName() + ":");
        GameLog.info(" " + desc.getAsString());
        if(target.isDestroyed()){
            removeActor(target);
        }
    }

    /**
     * Default attack handle
     * @param actor
     * @param target
     */
    private void attackWithWeapon(Actor actor, Actor target, Move move){
        //TODO make possible to swing attack while moving
        WeaponAttack weaponAttack = move instanceof Move.Rotate
                                        ? WeaponAttack.get(Direction.C,
                                                        move == Move.Rotate.CLOCKWISE
                                                        ? WeaponAttack.WeaponMove.SWING_CLOCKWISE
                                                        : WeaponAttack.WeaponMove.SWING_COUNTERCLOCKWISE)
                                        : WeaponAttack.get(((Move.Go)move).direction, WeaponAttack.WeaponMove.THRUST);
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
        if(target.isDestroyed()){
            removeActor(target);
        }
    }

    private static Coords getTargetLocationAndRotate(Actor a, Move m, Coords location) {
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

    private static boolean validLocation(Coords playerLocation, Tile[][] tiles) {
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
