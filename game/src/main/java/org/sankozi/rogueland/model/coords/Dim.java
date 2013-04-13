package org.sankozi.rogueland.model.coords;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dim)) return false;

        Dim dim = (Dim) o;
        return height == dim.height && width == dim.width;
    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        return result;
    }
}
