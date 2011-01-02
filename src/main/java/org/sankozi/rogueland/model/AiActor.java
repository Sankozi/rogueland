package org.sankozi.rogueland.model;

import java.awt.Point;
import java.util.Random;

/**
 *
 * @author sankozi
 */
public class AiActor implements Actor{

    Point location;
    Random rand = new Random();

    @Override
    public Move act(Level input) {
        return Move.Go.values()[rand.nextInt(Move.Go.values().length)];
    }

    @Override
    public Point getLocation() {
        return location;
    }

    @Override
    public void setLocation(Point point) {
        this.location = point;
    }
}
