package org.sankozi.rogueland.model.coords;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.AbstractSet;
import java.util.Iterator;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Class for creating immutable sets of coordinates
 *
 * @author sankozi
 */
public final class CoordsSets {
    private final static Logger LOG = LogManager.getLogger(CoordsSets.class);
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

