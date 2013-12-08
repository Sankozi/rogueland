package org.sankozi.rogueland.model.coords;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Rectangle with segments parallel or perpendicular to axis
 *
 * @author sankozi
 */
public final class Rect {
    private final static Logger LOG = LogManager.getLogger(Rect.class);

    private final Dim dim;
    /** corner with lowest coordinates (top left in screen coordinates)*/
    private final Coords start;

    private Rect(Dim dim, Coords start) {
        this.dim = checkNotNull(dim);
        this.start = checkNotNull(start);
    }

    /**
     * Creates Rect from top left corner (in screen coords) and Dim
     * @param coords top left corner coords
     * @param dim Dim of returned Rectangle
     * @return Rect instance
     */
    public static Rect fromCornerAndDim(Coords coords, Dim dim){
        return new Rect(dim, coords);
    }

    /**
     * Creates Rect from top left and bottom right corners
     * @param corner1 top left corner
     * @param corner2 bottom right corner
     * @return Rect instance
     */
    public static Rect fromTopAndBottomCorner(Coords corner1, Coords corner2){
        return new Rect(new Dim(corner2.x - corner1.x, corner2.y - corner2.y), corner1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rect rect = (Rect) o;
        return dim.equals(rect.dim) && start.equals(rect.start);
    }

    @Override
    public int hashCode() {
        int result = dim.hashCode();
        result = 31 * result + start.hashCode();
        return result;
    }

    public int getWidth(){
        return dim.width;
    }

    public int getHeight(){
        return dim.height;
    }

    public int getMinX() {
        return start.x;
    }

    public int getMinY() {
        return start.y;
    }

    public int getMaxX(){
        return start.x + dim.width;
    }

    public int getMaxY(){
        return start.y + dim.height;
    }

    public Dim getDim() {
        return dim;
    }
}
