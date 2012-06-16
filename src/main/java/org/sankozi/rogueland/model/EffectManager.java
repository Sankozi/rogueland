package org.sankozi.rogueland.model;

import com.google.common.collect.TreeMultimap;
import java.util.*;
import javax.annotation.Nullable;
import org.sankozi.rogueland.model.Destroyable.Param;

/**
 * Object that manages active Effects, it is responsible for:
 * - relationships between different effect and base params, for example
 * when param is changed this object reloads all dependent Effects
 * - it properly destroys all events that should have ended
 * @author sankozi
 */
public class EffectManager {
	private final static ResourceBundle paramsBundle = ResourceBundle.getBundle("org/sankozi/rogueland/resources/params");
	
    //EffectManager might work for different kinds of objects
	private final @Nullable Player player;
	private final @Nullable Actor actor;
	private final Destroyable destroyable;

	/** map storing time -> collection of effects that end at that time
	 *  (guava TreeMultimap is not navigable)  */
	private TreeMap<Float, Collection<Effect>> registeredEffects = new TreeMap<>();
	private float now;
	private IdentityHashMap<Effect, EffectContext> contexts = new IdentityHashMap<>();
			
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

	/**
	 * Puts effect inside registeredEffects
	 * @param effect 
	 */
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

	public ParamAccess accessDestroyableParam(Destroyable.Param param){
		return new DestroyableParameterAccess(param);
	}

	private class DestroyableParameterAccess implements ParamAccess{
		final Destroyable.Param param;
		float change = 0f;
		private ResourceBundle paramsBundle;

		public DestroyableParameterAccess(Param param) {
			this.param = param;
		}

		@Override public String cn() { return param.name(); }
			
		@Override public String name() { return paramsBundle.getString(cn()); }

		@Override public float get() { return destroyable.destroyableParam(param); }
		
		@Override
		public void setChange(float d) {
			destroyable.setDestroyableParam(param, -change + d); //removes previous change and add new
			change = d;
		}
	}
}
