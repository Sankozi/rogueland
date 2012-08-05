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

    private final EnumMap<Param, Float> params = new EnumMap<>(Param.class);
    private float durability;

    /**
     * Abstract destroyable constructor, created object will have
     * all resistances equal 0, MAX_HEALTH param equal to passed durability
     * @param durability initial durability
     */
    public AbstractDestroyable(int durability) {
        this.durability = durability;
        this.setDestroyableParam(Param.MAX_DURABILITY, durability);
        for(Type damage : Type.values()){
            params.put(damage.getResistanceParam(), 0f);
        }
    }

    @Override
    public final float destroyableParam(Param param) {
        return params.get(param);
    }

    @Override
    public final void setDestroyableParam(Param param, float value) {
        params.put(param, value);
    }

    @Override
    public int protection(Type type) {
        return (int) params.get(type.getResistanceParam()).floatValue();
    }

    @Override
    public void damage(int power) {
        durability -= power;
    }

    @Override
    public void heal(float value){
//        LOG.info(this.getName() + " : healing +" + fraction);
        durability += value;
        if(durability >= destroyableParam(Param.MAX_DURABILITY)){
            durability = (int) destroyableParam(Param.MAX_DURABILITY);
            LOG.info(this.getName() + ": MAX HEALTH");
        }
    }

    @Override
    public boolean isDestroyed() {
        return durability < 1f;
    }

    @Override
    public int getDurability() {
        return (int) durability;
    }
}
