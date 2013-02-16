package org.sankozi.rogueland.model;

import java.util.EnumMap;
import java.util.EnumSet;
import org.sankozi.rogueland.model.effect.Effect;

/**
 *
 * @author sankozi
 */
public final class TestUtils {

    public static Item createItem(String name, ItemType type) {
        EnumMap<Destroyable.Param, Float> params = new EnumMap<>(Destroyable.Param.class);
        params.put(Destroyable.Param.MAX_DURABILITY, 10f);
        ItemTemplate it = new ItemTemplateBuilder().setName(name).setDescription(name).setParams(params).setUsedEffect(Effect.NULL).setTypes(ItemType.expand(EnumSet.of(type))).createItemTemplate();
        return new Item(it);
    }


    private TestUtils(){}
}
