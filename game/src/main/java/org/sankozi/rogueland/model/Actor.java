package org.sankozi.rogueland.model;

import org.sankozi.rogueland.model.effect.Effect;
import org.sankozi.rogueland.model.effect.EffectManager;

/**
 * Object that acts, on each tile there can be only one actor
 * @author sankozi
 */
public interface Actor extends Destroyable{
    Move act(Level input, Locator locator);

    Coords getLocation();
    void setLocation(Coords point);

	/** returns power of close attacks */
    Effect getBumpEffect();

	/** returns power of weapon attacks */
	Effect getWeaponEffect();

    /** Returns actor param value */
    float actorParam(Param param);
    void setActorParam(Param param, float value);

	/** Returns true if Actor has weapon */
	boolean isArmed();

	Coords getWeaponLocation();

    EffectManager getEffectManager();

    Description getDescription();

    enum Param implements org.sankozi.rogueland.model.Param{
        MANA_REGEN,
    }
}
