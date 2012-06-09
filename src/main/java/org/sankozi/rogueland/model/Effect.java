package org.sankozi.rogueland.model;

/**
 * Base class of effects. Effect is an GameObject that changes parametrized 
 * GameObjects like Destroyable, Actor or Player
 * 
 * @author sankozi
 */
public abstract class Effect implements GameObject{

	/**
	 * Time at which this effect will end relative to start moment, 
	 * for permanent effects this method should return Float.POSITIVE_INFINITY 
	 * for instants should return 0f
	 * @return 
	 */
	public abstract float finishTime();

	/**
	 * Makes changes to attached object
	 * @param manager, not null
	 */
	public abstract void start(EffectManager manager);
	
	/**
	 * Ends this Effect, implementing classes may undo changes made in start method
	 * @param manager 
	 */
	public abstract void end(EffectManager manager);

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
}
