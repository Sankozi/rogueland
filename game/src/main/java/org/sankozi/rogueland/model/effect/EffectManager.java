package org.sankozi.rogueland.model.effect;

import java.util.*;
import javax.annotation.Nullable;

import org.sankozi.rogueland.model.*;
import org.sankozi.rogueland.model.Destroyable.Param;

/**
 * Object that manages active Effects, it is responsible for:
 * - relationships between different effect and base params, for example
 * when param is changed this object reloads all dependent Effects
 * - it properly destroys all events that should have ended
 * @author sankozi
 */
public class EffectManager implements AccessManager {
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

	private EffectManager(Destroyable d, @Nullable Actor a, @Nullable Player p) {
		this.destroyable = d;
		this.actor = a;
		this.player = p;
	}

	public static EffectManager forPlayer(Player p){
		return new EffectManager(p, p, p);
	}

    public static EffectManager forActor(AiActor a) {
        return new EffectManager(a, a, null);
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

	public Description registerEffect(Effect effect){
		putEffect(effect);
        Description ret = startEffect(effect);
		if(effect.getFinishTime() <= 0f){
			endEffect(effect);
		}
        return ret;
	}

    public void removeEffect(Effect effect) {
        endEffect(effect);
    }

	/**
	 * Starts effect with proper context
	 * @param effect 
	 */
	private Description startEffect(Effect effect) {
		currentContext = getEffectContext(effect);
		Description ret = effect.start(this);
		currentContext = null;
        return ret;
	}

	private void endEffect(Effect effect) {
		currentContext = getEffectContext(effect);
		effect.end(this);
		currentContext = null;
		contexts.remove(effect);
	}

	public void tick(){
		now += 1f;
		Map<Float, Collection<Effect>> head = registeredEffects.headMap(now, true);
		if(!head.isEmpty()){
			for(Collection<Effect> efs : head.values()){
				for(Effect ef : efs){
					endEffect(ef);
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
			contexts.put(currentContext.effect, currentContext);
			contextStored = true;
		}
	}
	
	public ParamAccess accessDestroyableParam(Destroyable.Param param){
		return new DestroyableParamAccess(param);
	}

    @Override
    public ParamAccess accessActorParam(Actor.Param param) {
        return new ActorParamAccess(param);
    }

    @Override
    public Damagable getDamagable() {
        return destroyable;
    }


	private static class EffectContext {
		final Effect effect;
		@Nullable Map<Destroyable.Param, Float> destroyableParamChanges;
		@Nullable Map<Actor.Param, Float> actorParamChanges;

		EffectContext(Effect effect) {
			this.effect = effect;
		}

		void putChange(Destroyable.Param param, float change) {
			if(destroyableParamChanges == null){
				if(change != 0f) {
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

        void putChange(Actor.Param param, float change) {
			if(actorParamChanges == null){
				if(change != 0f) {
					actorParamChanges = new EnumMap<>(Actor.Param.class);
					actorParamChanges.put(param, change);
				}
			} else {
				if(change == 0f) {
					actorParamChanges.remove(param);
				} else {
					actorParamChanges.put(param, change);
				}
			}
		}

        private float getChange(Actor.Param param) {
			if(actorParamChanges == null) {
				return 0f;
			}
			Float ret = actorParamChanges.get(param);
			if(ret == null) {
				return 0f;
			} else {
				return ret.floatValue();
			}
		}

		private float getChange(Destroyable.Param param) {
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

    private abstract class BaseParamAccess<T extends Enum> implements ParamAccess {
        final T param;

        public BaseParamAccess(T param) {
            this.param = param;
        }
        
		@Override public final String cn() { return param.name(); }
		@Override public final String name() { return paramsBundle.getString(cn()); }
    }

    private class ActorParamAccess extends BaseParamAccess<Actor.Param> implements ParamAccess {
        public ActorParamAccess(Actor.Param param) {
            super(param);
        }
        
		@Override public float get() { return actor.actorParam(param); }

        @Override
        public void setChange(float d) {
			actor.setActorParam(param, get() - currentContext.getChange(param) + d); //removes previous change and add new
            currentContext.putChange(param, d);
			if(d != 0f){
				storeCurrentContextCheck();
			}
        }
    }

	private class DestroyableParamAccess extends BaseParamAccess<Destroyable.Param> implements ParamAccess{
		public DestroyableParamAccess(Param param) {
            super(param);
		}

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
