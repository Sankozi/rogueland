package org.sankozi.rogueland.model.coords;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Size of object in 2D dimension
 *
 * @author sankozi
 */
public final class Dim {
    private final static Logger LOG = LogManager.getLogger(Dim.class);

    public final int width;
    public final int height;

    public Dim(int width, int height) {
        checkArgument(width >= 0, "width cannot be negative");
        checkArgument(height >= 0, "height cannot be negative");
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return width + " x " + height;
    }

    /**
     * Returns true if coordinates are within Rectange with (0,0) top left corner (screen coords) and this Dim
     * @param xy coordinates
     * @return true if point is inside Rectangle
     */
    public boolean containsCoordinates(Coords xy){
        return containsCoordinates(xy.x, xy.y);
    }

    /**
     * Returns true if coordinates are within Rectange with (0,0) top left corner (screen coords) and this Dim
     * @param x x coordinate
     * @param y y coordinate
     * @return true if point is inside Rectangle
     */
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
