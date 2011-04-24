package org.sankozi.rogueland.model;

import java.util.EnumMap;
import org.apache.log4j.Logger;
import org.sankozi.rogueland.model.Damage.Type;

/**
 * Base of all Destroyable classes,
 *
 * @author sankozi
 */
public abstract class AbstractDestroyable implements Destroyable{
    private final static Logger LOG = Logger.getLogger(AbstractDestroyable.class);

    private final EnumMap<Param, Integer> params = new EnumMap(Param.class);
    private int durability;
    private int durabilityFraction;

    /**
     * Abstract destroyable constructor, created object will have
     * all resistances equal 0, MAX_HEALTH param eqal to passed durability
     * @param durability initial durability
     */
    public AbstractDestroyable(int durability) {
        this.durability = durability;
        this.durabilityFraction = 0;
        this.setDestroyableParam(Param.MAX_HEALTH, durability);
        for(Type damage : Type.values()){
            params.put(damage.getResistanceParam(), 0);
        }
    }

    @Override
    public final int destroyableParam(Param param) {
        return params.get(param);
    }

    @Override
    public final void setDestroyableParam(Param param, int value) {
        params.put(param, value);
    }

    @Override
    public int protection(Type type) {
        return params.get(type.getResistanceParam());
    }

    @Override
    public void damage(int power) {
        durability -= power;
    }

    @Override
    public void healFraction(int fraction){
//        LOG.info(this.getName() + " : healing +" + fraction);
        durability += (fraction >> 10);
        durabilityFraction += fraction % 1024;

        if(durabilityFraction >= 1024){
            durability++;
            durabilityFraction -= 1024;
        }
        if(durability >= destroyableParam(Param.MAX_HEALTH)){
            durability = destroyableParam(Param.MAX_HEALTH);
            durabilityFraction = 0;
            LOG.info(this.getName() + ": MAX HEALTH");
        }
    }

    @Override
    public boolean isDestroyed() {
        return durability <= 0;
    }

    @Override
    public int getDurability() {
        return durability;
    }
}
