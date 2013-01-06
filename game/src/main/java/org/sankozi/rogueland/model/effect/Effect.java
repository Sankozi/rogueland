package org.sankozi.rogueland.model.effect;

import org.sankozi.rogueland.model.GameObject;

/**
 * Base class of effects. Effect is an GameObject that changes parametrized 
 * GameObjects like Destroyable, Actor or Player
 * 
 * @author sankozi
 */
public abstract class Effect implements GameObject{
	/** Null effect */
	public static final Effect NULL = new Effect(-1f){
		@Override public void start(AccessManager manager) {}
		@Override public void end(AccessManager manager) {}
		@Override public String getObjectName() { return "none"; }
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
	 */
	public abstract void start(AccessManager manager);
	
	/**
	 * Ends this Effect, implementing classes may undo changes made in start method
	 * @param manager 
	 */
	public abstract void end(AccessManager manager);

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
