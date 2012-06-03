package org.sankozi.rogueland.model;

/**
 * Interface od object that can be destroyed
 * @author sankozi
 */
public interface Destroyable extends GameObject{

    void damage(int power);

    int getDurability();

    /**
     * Increases durability by passed fraction
     * @param fraction amount to heal * 2^10
     */
    void heal(float value);

    int protection(Damage.Type type);

    boolean isDestroyed();

    float destroyableParam(Param param);
    void setDestroyableParam(Param param, float value);

    public enum Param {
        MAX_DURABILITY,
        DURABILITY_REGEN,

        /* Protections */

        PIERCING_PROT,
        SLASHING_PROT,
        BLUNT_PROT;
    }

}
