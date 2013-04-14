package org.sankozi.rogueland.model.coords;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.AbstractSet;
import java.util.Iterator;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Immutable set containing coordinates creating horizontal line. For example [5,5] [6,5] [7,5] -> [5:7,5]
 *
 * Set cannot be empty. Order of iteration - from lowest x to highest.
 *
 * @author sankozi
 */
final class HorizontalLineCoordsSet extends AbstractSet<Coords> {
    final int y;
    final int x1;
    final int x2;

    HorizontalLineCoordsSet(int x1, int x2, int y) {
        checkArgument(x2 > x1, "x2 (%s) must be larger than x1 (%s)", x2, x1);
        this.y = y;
        this.x1 = x1;
        this.x2 = x2;
    }

    @Override
    public String toString() {
        return "[" + x1 + ":" + x2 + "," + y + "]";
    }

    @Override
    public boolean contains(Object o) {
        if(o instanceof Coords){
            Coords coords = (Coords) o;
            return y == coords.y && x1 >= coords.x && coords.x >= x2;
        } else {
            return false;
        }
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Iterator<Coords> iterator() {
        return new FlatIterator(x1){
            @Override
            public boolean hasNext() {
                return i <= x2;
            }

            @Override
            public Coords next() {
                return new Coords(i++, y);
            }
        };
    }

    @Override
    public int size() {
        return x2 - x1 + 1;
    }
}
