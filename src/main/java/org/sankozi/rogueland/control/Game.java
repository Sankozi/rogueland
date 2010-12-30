package org.sankozi.rogueland.control;

import com.google.inject.internal.Preconditions;
import java.awt.Point;
import org.apache.log4j.Logger;
import org.sankozi.rogueland.model.Controls;
import org.sankozi.rogueland.model.Level;
import org.sankozi.rogueland.model.Move;
import org.sankozi.rogueland.model.Player;
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
            Move m = null;
            do {
                m = player.act(level);
                level.getTiles()[playerLocation.x][playerLocation.y].player = false;
                LOG.info("move : " + m);
                if(m == Move.Go.EAST){
                    playerLocation.x++;
                } else if(m == Move.Go.NORTH) {
                    playerLocation.y--;
                } else if(m == Move.Go.WEST) {
                    playerLocation.x--;
                } else if(m == Move.Go.SOUTH) {
                    playerLocation.y++;
                }
                level.getTiles()[playerLocation.x][playerLocation.y].player = true;
//                level.getTiles()[playerLocation.x][playerLocation.y].type = Type.GRASS;
                LOG.info("player location:" + playerLocation);
            } while (m != null);
        }
    }

    public Point getPlayerLocation() {
        return playerLocation;
    }
}
