package org.sankozi.rogueland.model;

import org.sankozi.rogueland.model.guid.Guid;
import org.sankozi.rogueland.model.guid.GuidGenerator;

import java.util.EnumSet;

/**
 * Type of an item - defines how item can be used
 * @author sankozi
 */
public enum ItemType implements Guid{
	/** can be held in one or both hands */
	HELD,

    WORN,
	WORN_FEET(WORN),
	WORN_LEGS(WORN),
	WORN_CHEST(WORN),
	WORN_HEAD(WORN),
	WORN_HANDS(WORN),
	WORN_FINGER(WORN),
	WORN_AROUND_NECK(WORN),
	WORN_AROUND_WAIST(WORN),
	/** can be used as a weapon*/	
	WEAPON(HELD),
    
    //=== WEAPON TYPES ===
    SWORD(WEAPON),
    MACE(WEAPON),
    STAFF(WEAPON),

    /** can be used, i.e. activated */
	USABLE;

    private final ItemType[] includes;
    private final int guid = GuidGenerator.getNewGuid();

    public static EnumSet<ItemType> expand(EnumSet<ItemType> set){
		EnumSet<ItemType> ret = EnumSet.copyOf(set);
		for(ItemType type : set){
			recAdd(ret, type);
		}
		return ret;
	}

	private static void recAdd(EnumSet<ItemType> set, ItemType type){
		set.add(type);
		for(ItemType included : type.includes){
            recAdd(set, included);
        }
	}

    ItemType(ItemType... includes){
        this.includes = includes;
    }

    @Override
    public int getGuid() {
        return guid;
    }
}
