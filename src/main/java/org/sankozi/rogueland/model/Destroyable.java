package org.sankozi.rogueland.model;

/**
 * Interface od object that can be destroyed
 * @author sankozi
 */
public interface Destroyable extends GameObject{

    void damage(int power);
    /**
     * Increases durability by passed fraction
     * @param fraction amount to heal * 2^10
     */
    void heal(int fraction);

    int getResistance(Damage.Type type);

    boolean isDestroyed();

    int destroyableParam(Param param);
    void setDestroyableParam(Param param, int value);

    public enum Param {
        MAX_HEALTH,
        HEALTH_REGEN,

        /* Protections */

        PIERCING_PROT,
        SLASHING_PROT,
        BLUNT_PROT;
    }

}
