package org.sankozi.rogueland.model;

/**
 *
 * @author sankozi
 */
public interface Move {

    public static enum Go implements Move{
        EAST,
        NORTH,
        WEST,
        SOUTH,
        NORTHEAST,
        NORTHWEST,
        SOUTHEAST,
        SOUTHWEST;
    }

}
