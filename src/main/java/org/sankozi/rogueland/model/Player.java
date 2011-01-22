package org.sankozi.rogueland.model;

import java.awt.Point;
import org.apache.log4j.Logger;

/**
 * Human Player
 * @author sankozi
 */
public class Player extends AbstractActor {
    private final static Logger LOG = Logger.getLogger(Player.class);

    private final Controls controls;
    private Point location;

    public Player(Controls controls){
        super(10);
        this.controls = controls;
    }

    @Override
    public Move act(Level input) {
        try {
            return controls.waitForMove();
        } catch (InterruptedException ex) {
            LOG.error(ex.getMessage(), ex);
            return null;
        }
    }

    @Override
    public Point getLocation() {
        return location;
    }

    @Override
    public void setLocation(Point point) {
        this.location = point;
    }

    @Override
    public String getName() {
        return "actor/player";
    }
}
