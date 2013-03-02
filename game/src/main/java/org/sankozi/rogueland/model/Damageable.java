package org.sankozi.rogueland.model;

/**
 * @author sankozi
 */
public interface Damageable {
    /**
	 * Reduces durability by passed value
	 * @param value 
	 */
    void damage(int value);

    /**
     * Increases durability by passed fraction, Durability never increases over
	 * MAX_DURABILITY Param
     * @param value
     */
    void heal(float value);

    int protection(Damage.Type type);
}
