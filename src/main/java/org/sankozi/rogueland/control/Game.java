package org.sankozi.rogueland.control;

import com.google.inject.internal.Preconditions;
import java.awt.Point;
import org.apache.log4j.Logger;
import org.sankozi.rogueland.model.Controls;
import org.sankozi.rogueland.model.Level;
import org.sankozi.rogueland.model.Move;
import org.sankozi.rogueland.model.Player;
import org.sankozi.rogueland.model.Tile;
import org.sankozi.rogueland.model.Tile.Type;

/**
 * Object responsible for single game instance
 * @author sankozi
 */
public class Game {
    private final static Logger LOG = Logger.getLogger(Game.class);

    Level level = new Level();
    Point playerLocation = null;
    Player player = null;
    Thread gameThread = new Thread(new GameRunnable());

    public void start(){
        Preconditions.checkState(!gameThread.isAlive(), "game has already started");
        gameThread.start();
    }

    public void setControls(Controls controls){
        this.player = new Player(controls);
        playerLocation = new Point(5,5);
    }

    public Level getLevel() {
        return level;
    }

    private class GameRunnable implements Runnable {
        @Override
        public void run() {
            Point newLocation;
            Move m = null;
            do {
                do {
                    level.getTiles()[playerLocation.x][playerLocation.y].player = true;
                    m = player.act(level);
                    newLocation = playerLocation.getLocation();
                    LOG.info("move : " + m);
                    processMove(m, newLocation);
                    level.getTiles()[playerLocation.x][playerLocation.y].player = false;
                } while (!validLocation(newLocation, level.getTiles()));
                playerLocation = newLocation;
                level.getTiles()[playerLocation.x][playerLocation.y].player = true;

//                LOG.info("player location:" + playerLocation);
            } while (m != null);
        }
    }

    private static void processMove(Move m, Point newLocation) {
        if (m == Move.Go.EAST) {
            newLocation.x++;
        } else if (m == Move.Go.NORTH) {
            newLocation.y--;
        } else if (m == Move.Go.WEST) {
            newLocation.x--;
        } else if (m == Move.Go.SOUTH) {
            newLocation.y++;
        }
    }

    private static boolean validLocation(Point playerLocation, Tile[][] tiles) {
        if(playerLocation.x < 0 || playerLocation.y < 0
                || playerLocation.x >= tiles.length
                || playerLocation.y >= tiles[0].length
                || tiles[playerLocation.x][playerLocation.y].type == Type.WALL){
//            LOG.info("invalid location : " + playerLocation);
            return false;
        } else {
            return true;
        }
    }

    public Point getPlayerLocation() {
        return playerLocation;
    }
}
