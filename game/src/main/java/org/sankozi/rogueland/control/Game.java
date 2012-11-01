package org.sankozi.rogueland.control;

import com.google.common.collect.Lists;
import java.util.List;
import org.apache.log4j.Logger;
import org.sankozi.rogueland.model.Destroyable.Param;
import org.sankozi.rogueland.model.*;

/**
 * Object responsible for single game instance
 * @author sankozi
 */
public class Game {
    private final static Logger LOG = Logger.getLogger(Game.class);

    private Level level = new Level(300, 200);
    private GameLog log = new GameLog();
    private Player player = null;
    private List<Actor> actors = Lists.newArrayList();
	private List<Actor> toBeRemoved = Lists.newArrayList();

	private Locator locator;

    private GameRunnable runningGame;

	{
		LevelGenerator.generate(level);
		locator = new GameLevelLocator(this, level);
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
        this.player = new Player(controls);
        player.setLocation(new Coords(5,5));
        actors.add(player);
        Actor ai = new AiActor();
        ai.setLocation(new Coords(10,10));
        actors.add(ai);
    }

    public void addLogListener(LogListener logListener){
        log.addListener(logListener);
    }

    private class GameRunnable implements Runnable {
        @Override
        public void run() {
            GameLog.initThreadLog(log);
            GameLog.info("Game has started");
			try {
				do {
					processActors();
				} while (!player.isDestroyed());
			} catch (IllegalStateException ex){
				if(ex.getCause() instanceof InterruptedException){
					LOG.info("game ended");
				}
			}
        }

		/**
		 * Process actor that is moving with its weapon (on different tile)
		 * @param actor 
		 */
		private void processActor(Actor actor) {
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
					interact(actor, tile.actor, actor.getPower());
				} else {
					actorLocation = targetLocation;
				}
				assert tiles[actorLocation.x][actorLocation.y].actor == null 
						: tiles[actorLocation.x][actorLocation.y].actor.getName() + " on point " + actorLocation;
				tiles[actorLocation.x][actorLocation.y].actor = actor;
				actor.setLocation(actorLocation);
			} 
			if(actor.isArmed()){
				Coords nextWeaponLocation = actor.getWeaponLocation();	

				if(!prevWeaponLocation.equals(nextWeaponLocation)){
					LOG.info("new weapon location : " + nextWeaponLocation.x + "," + nextWeaponLocation.y);
					Tile tile = tiles[nextWeaponLocation.x][nextWeaponLocation.y];
					tiles[prevWeaponLocation.x][prevWeaponLocation.y].weapon = false;
					tiles[nextWeaponLocation.x][nextWeaponLocation.y].weapon = true;
					if(tile.actor != null){
						interact(actor, tile.actor, actor.getWeaponPower());
					}
				}
			}
        }

        private void processActors() {
            for(Actor actor:actors){
                actor.heal(actor.destroyableParam(Param.DURABILITY_REGEN));
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
    private void interact(Actor actor, Actor target, Damage dam){
        float res = target.protection(dam.type);
        
        if(res < dam.value){
            GameLog.info(actor.getName() + " attacked " + target.getName() + " for " + dam + "[" + res + " resisted]");
            target.damage(dam.value - (int) res);
            if(target.isDestroyed()){
                GameLog.info(target.getName() + " is destroyed!");
                removeActor(target);
            }
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
            GameLog.info("Waiting");
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
            return tile.type != Tile.Type.WALL;
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
