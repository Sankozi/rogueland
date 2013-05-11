package org.sankozi.rogueland.model;

import org.sankozi.rogueland.model.guid.Guid;
import org.sankozi.rogueland.model.guid.GuidGenerator;

import javax.annotation.Nullable;

/**
 *
 * @author sankozi
 */
public final class Tile {
    public static enum Type implements Guid{
        ROCK,
        SAND;

        private final int guid = GuidGenerator.getNewGuid();

        public int getGuid() {
            return guid;
        }
    }
    public Type type = Type.SAND;
    public @Nullable Actor actor;
	public boolean weapon;

	public boolean isPassable(){
		return type != Type.ROCK && actor == null && !weapon;
	}

    public Description getDescription(){
        if(actor != null){
            return Description.stringDescription(actor.getName(), " on ", type.name(), "\n", actor.getDescription().getAsString());
        } else if(weapon){
            return Description.stringDescription("weapon above ", type.name());
        } else {
            return Description.stringDescription(type.name());
        }
    }

    @Override
    public String toString() {
        if(weapon){
            return "Tile with weapon";
        } else {
            return "Tile " + type + (actor == null? "" : " with " + actor);
        }
    }
}
