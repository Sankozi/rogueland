package org.sankozi.rogueland.model;

/**
 *
 * @author sankozi
 */
public interface Move {

    public static enum Go implements Move{
        NORTHWEST,
        NORTH,
        NORTHEAST,
        WEST,
        EAST,
        SOUTH,
        SOUTHEAST,
        SOUTHWEST;
    }

    public static final Move WAIT = new Move(){};

}
