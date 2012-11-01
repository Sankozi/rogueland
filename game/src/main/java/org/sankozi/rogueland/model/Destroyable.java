package org.sankozi.rogueland.model;

/**
 * Interface of object that can be damaged and destroyed
 * 
 * Object can be damaged only by integer value, but can be healed by fractions. 
 * Fractions doesn't count towards 'living' status ie Deatroyable with durability
 * lower than one is considered destroyed 
 * 
 * Fraction heals help with mechanics that heal one point every n turns.
 * 
 * @author sankozi
 */
public interface Destroyable extends GameObject{

	/**
	 * Reduces durability by passed value
	 * @param value 
	 */
    void damage(int value);

	/**
	 * current 'health' of an object/monster, when it is not higher than one pointGameObject 
	 * is usually considered as destroyed
	 * @return int
	 */
    int getDurability();

    /**
     * Increases durability by passed fraction, Durability never increases over
	 * MAX_DURABILITY Param
     * @param value
     */
    void heal(float value);

    int protection(Damage.Type type);

    boolean isDestroyed();

    float destroyableParam(Param param);
    void setDestroyableParam(Param param, float value);

    public enum Param {
		/**  maximum value of Durability for object */
        MAX_DURABILITY,
		/** number of durability poins regained each turn */
        DURABILITY_REGEN,

        /* === Protections === */
		//each kind of damage is reduced by value of coresponding param
        PIERCING_PROT,
        SLASHING_PROT,
        BLUNT_PROT;
    }
}
