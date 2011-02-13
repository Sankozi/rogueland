package org.sankozi.rogueland.model;

/**
 *
 * @author sankozi
 */
public final class Damage {

    public enum Type {
        PIERCING,
        SLASHING,
        BLUNT
    }

    public final Type type;
    public final int value;

    public Damage(Type type, int value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return value + " " + type + " damage";
    }

}
