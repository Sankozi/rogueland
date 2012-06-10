package org.sankozi.rogueland.model;

import com.google.common.collect.TreeMultimap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
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

	private TreeMap<Float, Collection<Effect>> registeredEffects = new TreeMap<>();
	private float now;
			
	private @Nullable EffectContext currentContext;

	public EffectManager(Player p) {
		this.destroyable = p;
		this.actor = p;
		this.player = p;
	}

	public static EffectManager forPlayer(Player p){
		EffectManager ret = new EffectManager(p);
		return ret;
	}

	private void putEffect(Effect effect) {
		Collection<Effect> col = registeredEffects.get(now + effect.getFinishTime());
		if(col == null) {
			col = new ArrayList<>(); 
			registeredEffects.put(now + effect.getFinishTime(), col);
		}
		col.add(effect);
	}

	public void registerEffect(Effect effect){
		putEffect(effect);
		effect.start(this);
	}

	public void tick(){
		now += 1f;
		Map<Float, Collection<Effect>> head = registeredEffects.headMap(now, true);
		if(!head.isEmpty()){
			for(Collection<Effect> efs : head.values()){
				for(Effect ef : efs){
					ef.end(this);
				}
			}
			for(float key : new ArrayList<>(head.keySet())){
				registeredEffects.remove(key);
			}
		}
	}

	private static class EffectContext {
		private final Effect effect;

		public EffectContext(Effect effect) {
			this.effect = effect;
		}
	}
}
