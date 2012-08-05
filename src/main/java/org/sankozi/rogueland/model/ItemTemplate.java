package org.sankozi.rogueland.model;

import java.util.EnumMap;
import org.sankozi.rogueland.model.Destroyable.Param;

/**
 * Immutable object representing stats of an item;
 * @author sankozi
 */
public final class ItemTemplate {
    private final EnumMap<Param, Float> params = new EnumMap<>(Param.class);

	float destroyableParam(Param param) {
		return params.get(param);
	}

	
}
