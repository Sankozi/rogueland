package org.sankozi.rogueland.model;

import javax.annotation.Nullable;

/**
 * Object that manages active Effects, it is responsible for:
 * - relationships between different effect and base params, for example
 * when param is changed this object reloads all dependent Effects
 * - it properly destroys all events that should have ended
 * @author sankozi
 */
public class EffectManager {
    //EffectManager might work for different kinds of objects
	private final @Nullable Player player;
	private final @Nullable Actor actor;
	private final Destroyable destroyable;

	public EffectManager(Player p) {
		this.destroyable = p;
		this.actor = p;
		this.player = p;
	}

	public static EffectManager forPlayer(Player p){
		EffectManager ret = new EffectManager(p);
		return ret;
	}

	
}
