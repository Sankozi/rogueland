package org.sankozi.rogueland.model.coords;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Class for creating immutable sets of coordinates
 *
 * @author sankozi
 */
public final class CoordsSets {
    private final static Logger LOG = LogManager.getLogger(CoordsSets.class);

    private CoordsSets(){}

    public static Set<Coords> horizontalLine(int x1, int x2, int y){
        return new HorizontalLineCoordsSet(x1, x2, y);
    }

    public static Set<Coords> verticalLine(int x, int y1, int y2){
        return new VerticalLineCoordsSet(x, y1, y2);
    }

    public static Set<Coords> rectangle(int xMin, int yMin, int xMax, int yMax){
        return new RectangleCoordsSet(xMin, yMin, xMax, yMax);
    }
}

abstract class FlatIterator implements Iterator<Coords>{
    int i;

    protected FlatIterator(int i) {
        this.i = i;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

