package org.sankozi.rogueland.model;

/**
 *
 * @author sankozi
 */
public class Tile {

    public static enum Type {
        WALL,
        FLOOR,
        GRASS
    }
    public Type type = Type.GRASS;
    public Actor actor;

    @Override
    public String toString() {
        return "Tile[" + "type=" + type + ",actor=" + actor + ']';
    }
}
