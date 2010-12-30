package org.sankozi.rogueland.model;

import org.apache.log4j.Logger;

/**
 * Human Player
 * @author sankozi
 */
public class Player implements Actor {
    private final static Logger LOG = Logger.getLogger(Player.class);

    private final Controls controls;

    public Player(Controls controls){
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
}
