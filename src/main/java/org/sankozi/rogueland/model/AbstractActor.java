package org.sankozi.rogueland.model;

/**
 * Base of implementation of Actor class
 * @author sankozi
 */
public abstract class AbstractActor implements Actor{
    int durability;

    public AbstractActor(int durability) {
        this.durability = durability;
    }

    @Override
    public void damage(int power) {
        durability -= power;
    }

    @Override
    public boolean isDestroyed() {
        return durability <= 0;
    }
}
