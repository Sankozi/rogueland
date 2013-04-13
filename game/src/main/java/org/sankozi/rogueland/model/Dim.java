package org.sankozi.rogueland.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 2D dimension
 *
 * @author sankozi
 */
public final class Dim {
    private final static Logger LOG = LogManager.getLogger(Dim.class);

    public final int width;
    public final int height;

    public Dim(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return width + " x " + height;
    }

    public boolean containsCoordinates(Coords xy){
        return containsCoordinates(xy.x, xy.y);
    }

    public boolean containsCoordinates(int x, int y){
        return 0 <= x && x < width && 0 <= y && y < height;
    }
}
