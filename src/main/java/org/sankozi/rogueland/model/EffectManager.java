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
	private final IdentityHashMap<Effect, EffectContext> contexts = new IdentityHashMap<>();
	private @Nullable EffectContext currentContext;
	private boolean contextStored;

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
		startEffect(effect);
	}

	/**
	 * Starts effect with proper context
	 * @param effect 
	 */
	private void startEffect(Effect effect) {
		currentContext = getEffectContext(effect);
		effect.start(this);
		currentContext = null;
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

	/**
	 * Gets stored context or creates new one, contexts are created only if needed
	 * @param effect
	 * @return 
	 */
	private EffectContext getEffectContext(Effect effect){
		EffectContext ret = contexts.get(effect);
		if(ret == null){
			ret = new EffectContext(effect);
			contextStored = false;
		} else {
			contextStored = true;
		}
		return ret;
	}

	private void storeCurrentContextCheck(){
		if(!contextStored){
			
		}
	}
	
	public ParamAccess accessDestroyableParam(Destroyable.Param param){
		return new DestroyableParamAccess(param);
	}

	private static class EffectContext {
		final Effect effect;
		@Nullable Map<Destroyable.Param, Float> destroyableParamChanges;

		EffectContext(Effect effect) {
			this.effect = effect;
		}

		void putChange(Destroyable.Param param, float change) {
			if(destroyableParamChanges == null){
				if(change == 0f) {
					return;
				} else {
					destroyableParamChanges = new EnumMap<>(Destroyable.Param.class);
					destroyableParamChanges.put(param, change);
				}
			} else {
				if(change == 0f) {
					destroyableParamChanges.remove(param);
				} else {
					destroyableParamChanges.put(param, change);
				}
			}
		}

		private float getChange(Param param) {
			if(destroyableParamChanges == null) {
				return 0f;
			}
			Float ret = destroyableParamChanges.get(param);
			if(ret == null) {
				return 0f;
			} else {
				return ret.floatValue();
			}
		}
	}

	private class DestroyableParamAccess implements ParamAccess{
		final Destroyable.Param param;

		public DestroyableParamAccess(Param param) {
			this.param = param;
		}

		@Override public String cn() { return param.name(); }
			
		@Override public String name() { return paramsBundle.getString(cn()); }

		@Override public float get() { return destroyable.destroyableParam(param); }
		
		@Override
		public void setChange(float d) {
			destroyable.setDestroyableParam(param, get() - currentContext.getChange(param) + d); //removes previous change and add new
			currentContext.putChange(param, d);
			if(d != 0f){
				storeCurrentContextCheck();
			}
		}
	}
}
