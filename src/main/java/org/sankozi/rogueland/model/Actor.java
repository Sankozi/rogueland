package org.sankozi.rogueland.model;

import java.awt.Point;

/**
 *
 * @author sankozi
 */
public interface Actor extends Destroyable{
    Move act(Level input, Locator locator);

    Coords getLocation();
    void setLocation(Coords point);

	/** returns power of close attacks */
    Damage getPower();

	/** returns power of weapon attacks */
	Damage getWeaponPower();

    /** Returns value stored in int */
    float actorParam(Param param);

	/** Returns true if Actor has weapon */
	boolean isArmed();

	Point getWeaponLocation();

    enum Param {
        DAMAGE_MIN,
        DAMAGE_MAX,
       
        MANA_REGEN,
    }
}
