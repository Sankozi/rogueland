package org.sankozi.rogueland.model;

/**
 * Interface od object that can be destroyed
 * @author sankozi
 */
public interface Destroyable extends GameObject{

    void damage(int power);

    int getResistance(Damage.Type type);

    boolean isDestroyed();

}
