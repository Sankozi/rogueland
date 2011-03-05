package org.sankozi.rogueland.model;

import java.util.EnumMap;
import org.sankozi.rogueland.model.Damage.Type;

/**
 *
 * @author sankozi
 */
public abstract class AbstractDestroyable implements Destroyable{
    private final EnumMap<Param, Integer> params = new EnumMap(Param.class);
    private int durability;

    public AbstractDestroyable(int durability) {
        this.durability = durability;
    }

    @Override
    public int destroyableParam(Param param) {
        return params.get(param);
    }

    @Override
    public int getResistance(Type type) {
        return params.get(type.getResistanceParam());
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
