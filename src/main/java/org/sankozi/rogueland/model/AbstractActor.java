package org.sankozi.rogueland.model;

import java.util.EnumMap;
import org.sankozi.rogueland.model.Damage.Type;

/**
 * Base of implementation of Actor class
 * @author sankozi
 */
public abstract class AbstractActor implements Actor{
    private int durability;
    private final EnumMap<Damage.Type, Integer> resistances = new EnumMap(Damage.Type.class);

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

    @Override
    public int getResistance(Type type) {
        if(resistances.containsKey(type)){
            return resistances.get(type);
        } else {
            return 0;
        }
    }

    protected void setResistance(Type type, int value){
        resistances.put(type, value);
    }
}
