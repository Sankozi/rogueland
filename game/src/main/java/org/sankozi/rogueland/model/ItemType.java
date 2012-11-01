package org.sankozi.rogueland.model;

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
	WEAPON,
    /** can be used */
	USABLE
}
