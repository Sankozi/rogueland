package org.sankozi.rogueland.model.effect;

import org.sankozi.rogueland.model.Description;
import org.sankozi.rogueland.model.GameObject;
import org.sankozi.rogueland.model.Param;

import java.util.Collections;
import java.util.Map;

/**
 * Base class of effects. Effect is an GameObject that changes parametrized 
 * GameObjects like Destroyable, Actor or Player
 * 
 * @author sankozi
 */
public abstract class Effect implements GameObject{
	/** Null effect */
	public static final Effect NULL = new Effect(-1f){
		@Override public Description start(AccessManager manager) { return Description.EMPTY; }
		@Override public void end(AccessManager manager) {}
		@Override public String getObjectName() { return "none"; }
        @Override public Map<Param, Float> getDescriptionParameters(){ return Collections.emptyMap(); }
	};
			
	protected final float finishTime;

	protected Effect(float finishTime) {
		this.finishTime = finishTime;
	}

	/**
	 * Time at which this effect will end relative to start moment, 
	 * for permanent effects this method should return Float.POSITIVE_INFINITY 
	 * for instants should return 0f
	 * @return 
	 */
	public float getFinishTime(){
		return finishTime;
	}

	/**
	 * Makes changes to attached object
	 * @param manager, not null
     * @return
	 */
	public abstract Description start(AccessManager manager);
	
	/**
	 * Ends this Effect, implementing classes may undo changes made in start method
	 * @param manager 
	 */
	public abstract void end(AccessManager manager);

    /**
     * Return parameters that can be used to describe an effect
     * @return Map containing values of certain parameters, can be empty, never null
     */
    public abstract Map<? extends Param, Float> getDescriptionParameters();

	/**
	 * If this Effect is based on parameters of attached GameObject this method 
	 * will be called when change in dependant values is detected.
	 * 
	 * By default start method is called again.
	 * 
	 * @param manager 
	 */
	public void reload(EffectManager manager) {
		start(manager);
	}

	/**
	 * Two effects are equal if they are same instances
	 * @param obj
	 * @return 
	 */
	@Override
	public final boolean equals(Object obj) {
		return this == obj;
	}

	@Override
	public final int hashCode() {
		return super.hashCode();
	}
}
