package org.sankozi.rogueland.model;

/**
 * Interface of object that can be damaged and destroyed
 * 
 * Object can be damaged only by integer value, but can be healed by fractions. 
 * Fractions doesn't count towards 'living' status ie Destroyable with durability
 * lower than one is considered destroyed 
 * 
 * Fraction heals help with mechanics that heal one point every n turns.
 * 
 * @author sankozi
 */
public interface Destroyable extends GameObject, Damageable {

	/**
	 * current 'health' of an object/monster, when it is not higher than one pointGameObject 
	 * is usually considered as destroyed
	 * @return int
	 */
    int getDurability();
    boolean isDestroyed();

    float destroyableParam(Param param);
    void setDestroyableParam(Param param, float value);

    /**
     * Returns name for user
     * @return not null string
     */
    String getName();

    public enum Param implements org.sankozi.rogueland.model.Param{
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
