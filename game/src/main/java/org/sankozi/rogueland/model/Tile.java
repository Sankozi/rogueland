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
	public boolean weapon;

	public boolean isPassable(){
		return type != Type.WALL && actor == null && !weapon;
	}

    public Description getDescription(){
        if(actor != null){
            return Description.stringDescription(actor.getName(), " on ", type.name());
        } else if(weapon){
            return Description.stringDescription("weapon above ", type.name());
        } else {
            return Description.stringDescription(type.name());
        }
    }

    @Override
    public String toString() {
        return "Tile[" + "type=" + type + ",actor=" + actor + ']';
    }
}
