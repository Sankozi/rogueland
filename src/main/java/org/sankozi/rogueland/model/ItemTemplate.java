package org.sankozi.rogueland.model;

import java.util.EnumMap;
import java.util.EnumSet;
import javax.annotation.Nullable;
import org.sankozi.rogueland.model.Destroyable.Param;

/**
 * Immutable object representing various parameters and characteristics of an item;
 * @author sankozi
 */
final class ItemTemplate {
	/** Destroyable parameters of an item */
    private final EnumMap<Param, Float> params;
	/** Effect of an item when it is worn, wield, or used can be null if types 
	 *  doesn't contain any of above */ 
	private final @Nullable Effect effect;

	private final EnumSet<ItemType> types;

	ItemTemplate(EnumMap<Param, Float> params, @Nullable Effect effect, EnumSet<ItemType> types) {
		this.params = params;
		this.effect = effect;
		this.types = types;
	}

	float destroyableParam(Param param) {
		return params.get(param);
	}

	Iterable<ItemType> getTypes() {
		return types;
	}
}
