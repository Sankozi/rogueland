package org.sankozi.rogueland.model;

import java.util.EnumMap;
import java.util.EnumSet;
import org.sankozi.rogueland.model.Destroyable.Param;

/**
 * Immutable object representing stats of an item;
 * @author sankozi
 */
public final class ItemTemplate {
	/** Destroyable parameters of an item */
    private final EnumMap<Param, Float> params;
	/** Effect of an item */ 
	private final Effect effect;

	private final EnumSet<ItemType> types;

	public ItemTemplate(EnumMap<Param, Float> params, Effect effect, EnumSet<ItemType> types) {
		this.params = params;
		this.effect = effect;
		this.types = types;
	}

	float destroyableParam(Param param) {
		return params.get(param);
	}
}
