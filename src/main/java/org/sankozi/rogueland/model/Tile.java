package org.sankozi.rogueland.model;

/**
 *
 * @author sankozi
 */
public final class Tile {

    public static enum Type {
        WALL,
        FLOOR,
        GRASS
    }
    public Type type = Type.GRASS;
    public Actor actor;

	public boolean isPassable(){
		return type != Type.WALL && actor == null;
	}

    @Override
    public String toString() {
        return "Tile[" + "type=" + type + ",actor=" + actor + ']';
    }
}
