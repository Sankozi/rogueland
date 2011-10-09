package org.sankozi.rogueland.model;

import java.util.EnumMap;

/**
 * Base of implementation of Actor class
 * @author sankozi
 */
public abstract class AbstractActor extends AbstractDestroyable implements Actor{
    private final EnumMap<Actor.Param, Float> params = new EnumMap<>(Actor.Param.class);

    public AbstractActor(int durability) {
        super(durability);
    }

    @Override
    public float actorParam(Actor.Param param) {
        return params.get(param);
    }
//
//    protected void setResistance(Type type, int value){
//        params.put(type.getResistanceParam(), value);
//    }
}
