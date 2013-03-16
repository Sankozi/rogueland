package org.sankozi.rogueland.model;

import org.sankozi.rogueland.model.effect.Effect;

import java.util.EnumMap;
import java.util.EnumSet;

import static com.google.common.base.Preconditions.*;

public class ItemTemplateBuilder {
    String name;
    String description;
    EnumMap<Destroyable.Param, Float> params;
    Effect usedEffect = Effect.NULL;
    EnumSet<ItemType> types;
    Effect weaponEffect = Effect.NULL;

    public ItemTemplateBuilder setName(String name) {
        checkNotNull(name, "name cannot be null");
        this.name = name;
        return this;
    }

    public ItemTemplateBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ItemTemplateBuilder setParams(EnumMap<Destroyable.Param, Float> params) {
        checkArgument(params.get(Destroyable.Param.MAX_DURABILITY) != null, "params must contain MAX_DURABILITY (name=%s)", name);
        this.params = params;
        return this;
    }

    public ItemTemplateBuilder setUsedEffect(Effect usedEffect) {
        this.usedEffect = usedEffect;
        return this;
    }

    public ItemTemplateBuilder setTypes(EnumSet<ItemType> types) {
        this.types = ItemType.expand(types);
        return this;
    }

    public ItemTemplateBuilder setWeaponEffect(Effect weaponEffect) {
        this.weaponEffect = weaponEffect;
        return this;
    }

    public ItemTemplate createItemTemplate() {
        return new ItemTemplate(this);
    }
}