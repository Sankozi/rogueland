package org.sankozi.rogueland.model;

import org.sankozi.rogueland.control.Locator;
import org.sankozi.rogueland.model.coords.Coords;
import org.sankozi.rogueland.model.coords.Direction;
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
    public Iterable<Effect> getWeaponEffects(WeaponAttack attackType);

    /** Returns actor param value */
    float actorParam(Param param);
    void setActorParam(Param param, float value);

	/** Returns true if Actor has weapon */
	boolean isArmed();

	Coords getWeaponLocation();
    Direction getWeaponDirection();

    EffectManager getEffectManager();

    Description getDescription();

    enum Param implements org.sankozi.rogueland.model.Param{
        MANA_REGEN,
        /** maximum value of balance */
        MAX_BALANCE,
        /** current value of balance */
        BALANCE,
        /** amount of balance regenerated each turn */
        BALANCE_REGEN,
        /** flag (>0) indicating push force */
        OFF_BALANCE,
        /** push force toward east (negative - abs toward west) */
        PUSH_VERTICAL,
        /** push force toward south (negative - abs toward north)*/
        PUSH_HORIZONTAL,
    }
}
