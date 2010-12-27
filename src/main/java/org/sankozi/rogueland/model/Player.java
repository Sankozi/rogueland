package org.sankozi.rogueland.model;

/**
 * Human Player
 * @author sankozi
 */
public class Player implements Actor {

    private final Controls controls;

    public Player(Controls controls){
        this.controls = controls;
    }

    @Override
    public Move act(Level input) {
        return controls.waitForMove();
    }
}
