package org.sankozi.rogueland.control;

import com.google.common.collect.Lists;
import java.awt.Point;
import java.util.List;
import org.apache.log4j.Logger;
import org.sankozi.rogueland.model.Actor;
import org.sankozi.rogueland.model.AiActor;
import org.sankozi.rogueland.model.Controls;
import org.sankozi.rogueland.model.Damage;
import org.sankozi.rogueland.model.Destroyable.Param;
import org.sankozi.rogueland.model.Direction;
import org.sankozi.rogueland.model.Level;
import org.sankozi.rogueland.model.LevelGenerator;
import org.sankozi.rogueland.model.Locator;
import org.sankozi.rogueland.model.Move;
import org.sankozi.rogueland.model.Player;
import org.sankozi.rogueland.model.Tile;

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
        player.setLocation(new Point(5,5));
        actors.add(player);
        Actor ai = new AiActor();
        ai.setLocation(new Point(10,10));
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

        private void processActor(Actor actor) {
            Move m;
            Point newLocation;
            Point actorLocation = actor.getLocation();
            final Tile[][] tiles = level.getTiles();
            do {
                tiles[actorLocation.x][actorLocation.y].actor = actor;
                m = actor.act(level, locator);
                newLocation = actorLocation.getLocation();
                processMove(actor, m, newLocation);
//                LOG.info("actor : " + actor + " move : " + newLocation);
                tiles[actorLocation.x][actorLocation.y].actor = null;
            } while (!validLocation(newLocation, tiles));

            Tile tile = tiles[newLocation.x][newLocation.y];
            if(tile.actor != null){
                interact(actor, tile.actor, actor.getPower());
            } else {
                actorLocation = newLocation;
            }
            tiles[actorLocation.x][actorLocation.y].actor = actor;
            actor.setLocation(actorLocation);
        }

		/**
		 * Process actor that is moving with its weapon (on different tile)
		 * @param actor 
		 */
		private void processArmedActor(Actor actor) {
            Move m;
            Point newLocation;
            Point actorLocation = actor.getLocation();
			Point prevWeaponLocation = actor.getWeaponLocation();	
            final Tile[][] tiles = level.getTiles();
            do {
                tiles[actorLocation.x][actorLocation.y].actor = actor;
                m = actor.act(level, locator);
                newLocation = actorLocation.getLocation();
                processMove(actor, m, newLocation);
//                LOG.info("actor : " + actor + " move : " + newLocation);
                tiles[actorLocation.x][actorLocation.y].actor = null;
            } while (!validLocation(newLocation, tiles));

			if(!newLocation.equals(actorLocation)){
				Tile tile = tiles[newLocation.x][newLocation.y];
				if(tile.actor != null){
					interact(actor, tile.actor, actor.getPower());
				} else {
					actorLocation = newLocation;
				}
				tiles[actorLocation.x][actorLocation.y].actor = actor;
				actor.setLocation(actorLocation);
			}
			Point nextWeaponLocation = actor.getWeaponLocation();	

			if(!prevWeaponLocation.equals(nextWeaponLocation)){
				LOG.info("new weapon location : " + nextWeaponLocation.x + "," + nextWeaponLocation.y);
				Tile tile = tiles[nextWeaponLocation.x][nextWeaponLocation.y];
				level.getTiles()[prevWeaponLocation.x][prevWeaponLocation.y].weapon = false;
				level.getTiles()[nextWeaponLocation.x][nextWeaponLocation.y].weapon = true;
				if(tile.actor != null){
					interact(actor, tile.actor, actor.getWeaponPower());
				}
			}
        }

        private void processActors() {
            for(Actor actor:actors){
                actor.heal(actor.destroyableParam(Param.HEALTH_REGEN));
				if(!actor.isDestroyed()){
					if(actor.isArmed()){
						processArmedActor(actor);
						
					} else {
						processActor(actor);
					}
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

    private static void processMove(Actor a, Move m, Point newLocation) {
        if(m instanceof Move.Go){
            switch ((Move.Go) m) {
                case EAST:
                    newLocation.x++;
                    break;
                case NORTH:
                    newLocation.y--;
                    break;
                case WEST:
                    newLocation.x--;
                    break;
                case SOUTH:
                    newLocation.y++;
                    break;
                case NORTHEAST:
                    newLocation.y--;
                    newLocation.x++;
                    break;
                case NORTHWEST:
                    newLocation.y--;
                    newLocation.x--;
                    break;
                case SOUTHEAST:
                    newLocation.y++;
                    newLocation.x++;
                    break;
                case SOUTHWEST:
                    newLocation.y++;
                    newLocation.x--;
                    break;
                default:
                    LOG.error("unhandled move : " + m);
            }
        } else if(m instanceof Move.Rotate) {
			Player p = (Player) a;
			Direction dir = p.getWeaponDirection();
			switch ((Move.Rotate) m) {
				case CLOCKWISE:
					p.setWeaponDirection(dir.nextClockwise());
					return;
				case COUNTERCLOCKWISE:
					p.setWeaponDirection(dir.prevClockwise());
					return;
			}
		} else if(m == Move.WAIT) {
            GameLog.info("Waiting");
        } else {
            LOG.error("unhandled move : " + m);
        }
    }

    private static boolean validLocation(Point playerLocation, Tile[][] tiles) {
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
