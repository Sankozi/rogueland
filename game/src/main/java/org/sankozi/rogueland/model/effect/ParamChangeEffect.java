package org.sankozi.rogueland.model.effect;

import com.google.common.base.Suppliers;
import org.sankozi.rogueland.model.Damage;
import org.sankozi.rogueland.model.Description;
import org.sankozi.rogueland.model.Param;
import org.sankozi.rogueland.model.effect.AccessManager;
import org.sankozi.rogueland.model.effect.Effect;
import java.util.EnumMap;
import java.util.Map;
import org.sankozi.rogueland.model.Destroyable;

/**
 * 
 * @author sankozi
 */
public final class ParamChangeEffect extends Effect {
    private final String name;
    private final Map<Destroyable.Param, Float> changes;

    /** creates infinite version of effect (usually for items) */
    public ParamChangeEffect(String name, Map<Destroyable.Param, Float> changes) {
		super(Float.POSITIVE_INFINITY);
        this.name = name;
        this.changes = new EnumMap<>(changes);
	}

	public ParamChangeEffect(String name, float finishTime, Map<Destroyable.Param, Float> changes) {
		super(finishTime);
        this.name = name;
        this.changes = new EnumMap<>(changes);
	}
	
	@Override
	public Description start(AccessManager manager) {
        for(Map.Entry<Destroyable.Param, Float> entry : changes.entrySet()){
            manager.accessDestroyableParam(entry.getKey()).setChange(entry.getValue());
        }
        return Description.stringDescription(Suppliers.memoize(() -> {
            StringBuilder sb = new StringBuilder();
            for(Param param : changes.keySet()){
                sb.append(param.toString()).append(" - ").append(changes.get(param)).append('\n');
            }
            return sb.toString();
        }));
	}

	@Override
	public void end(AccessManager manager) {
        for(Destroyable.Param param : changes.keySet()){
            manager.accessDestroyableParam(param).setChange(0f);
        }
	}

    @Override
    public Map<Destroyable.Param, Float> getDescriptionParameters() {
        return changes;
    }

    @Override
	public String getObjectName() {
		return name;
	}
}
