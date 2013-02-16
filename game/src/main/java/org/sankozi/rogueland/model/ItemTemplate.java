package org.sankozi.rogueland.model;

import com.google.common.collect.ImmutableMap;
import org.sankozi.rogueland.model.effect.Effect;

import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

import org.sankozi.rogueland.model.Destroyable.Param;

import static com.google.common.base.Preconditions.*;
/**
 * Immutable object representing various parameters and characteristics of an item;
 * @author sankozi
 */
public final class ItemTemplate {
	private final String name;
    private final String description;
	/** Destroyable parameters of an item */
    private final EnumMap<Param, Float> params;
	/** Effect of an item when it is worn or held*/
    private final Effect usedEffect;
    private final Effect weaponEffect;

	private final EnumSet<ItemType> types;

    ItemTemplate(ItemTemplateBuilder builder){
        this.name = builder.name;
        this.description = builder.description;
        this.params = builder.params;
        this.usedEffect = builder.usedEffect;
        this.weaponEffect = builder.weaponEffect;
        this.types = builder.types;
    }

	float destroyableParam(Param param) {
		return params.get(param);
	}

	Iterable<ItemType> getTypes() {
		return types;
	}

	String getName() {
		return name;
	}

    /**
     * Effect when Item is used (i.e. worn or held)
     */
    Effect getUsedEffect() {
        return usedEffect;
    }

    /**
     * Effect when Item is used as a weapon (effect that is inflicted on enemies)
     * @return
     */
    Effect getWeaponEffect() {
        return weaponEffect;
    }

    String getDescription() {
        return description;
    }
}
