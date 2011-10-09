package org.sankozi.rogueland.model;

import java.awt.Point;

/**
 *
 * @author sankozi
 */
public interface Actor extends Destroyable{
    Move act(Level input, Locator locator);

    Point getLocation();
    void setLocation(Point point);

	/** returns power of close attacks */
    Damage getPower();

	/** returns power of weapon attacks */
	Damage getWeaponPower();

    /** Returns value stored in int */
    public float actorParam(Param param);

	/** Returns true if Actor has weapon */
	public boolean isArmed();

	public Point getWeaponLocation();

    public enum Param {
        DAMAGE_MIN,
        DAMAGE_MAX,
       
        MANA_REGEN,
    }
}
