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
        Float ret = params.get(param);
        if(ret == null){
            return 0f;
        } else {
            return ret.floatValue();
        }
    }

    @Override
    public void setActorParam(Actor.Param param, float value) {
        params.put(param, value);
    }
}
