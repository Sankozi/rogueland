package org.sankozi.rogueland.model.coords;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.AbstractSet;
import java.util.Iterator;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Immutable set containing coordinates creating vertical line. For example [5,5] [5,6] [5,7] -> [5,5:7]
 *
 * Set cannot be empty. Order of iteration - from lowest x to highest.
 *
 * @author sankozi
 */
public class VerticalLineCoordsSet extends AbstractSet<Coords> {
    private final static Logger LOG = LogManager.getLogger(VerticalLineCoordsSet.class);

    final int x;
    final int y1;
    final int y2;

    public VerticalLineCoordsSet(int x, int y1, int y2) {
        checkArgument(y2 > y1);
        this.x = x;
        this.y1 = y1;
        this.y2 = y2;
    }

    @Override
    public String toString() {
        return "[" + x + "," + y1 + ":" + y2 + "]";
    }

    @Override
    public boolean contains(Object o) {
        if(o instanceof Coords){
            Coords coords = (Coords) o;
            return x == coords.x && y1 >= coords.y && coords.y >= y2;
        } else {
            return false;
        }
    }

    @Override
    public int size() {
        return y2 - y1 + 1;
    }

    @Override
    public Iterator<Coords> iterator() {
        return new FlatIterator(y1){
            @Override
            public boolean hasNext() {
                return i <= y2;
            }

            @Override
            public Coords next() {
                return new Coords(x, i++);
            }
        };
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
