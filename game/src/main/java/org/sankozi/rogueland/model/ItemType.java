package org.sankozi.rogueland.model;

import java.util.EnumSet;

/**
 * Type of an item - defines how item can be used
 * @author sankozi
 */
public enum ItemType {
	/** can be held in one or both hands */
	HELD,

	WORN_FEET,
	WORN_LEGS,
	WORN_CHEST,
	WORN_HEAD,
	WORN_HANDS,
	WORN_FINGER,
	WORN_AROUND_NECK,
	WORN_AROUND_WAIST,
	/** can be used as a weapon*/	
	WEAPON(HELD),
    /** can be used */
	USABLE;

    private final ItemType[] includes;

    static EnumSet<ItemType> expand(EnumSet<ItemType> set){
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
}
