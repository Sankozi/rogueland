package org.sankozi.rogueland.model;

import com.google.common.collect.Sets;
import com.google.inject.internal.ImmutableMap;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.sankozi.rogueland.model.effect.EffectManager;

/**
 *
 * @author sankozi
 */
public final class EquippedItems {
    /** number of items that can be equipped */
    private final EnumMap<ItemType, Integer> slots = new EnumMap(ImmutableMap.<ItemType, Integer>builder()
                .put(ItemType.HELD, 1)
                .put(ItemType.WORN_AROUND_NECK, 1)
                .put(ItemType.WORN_AROUND_WAIST, 1)
                .put(ItemType.WORN_CHEST, 1)
                .put(ItemType.WORN_FEET, 1)
                .put(ItemType.WORN_FINGER, 2)
                .put(ItemType.WORN_LEGS, 1)
                .build());

    private final Set<Item> equippedItems = Sets.newHashSetWithExpectedSize(slots.size());

    private final EffectManager manager;
    private final Inventory equipment;

    public EquippedItems(EffectManager manager, Inventory equipment) {
        this.manager = manager;
        this.equipment = equipment;
    }

    /**
     * Tries to equip item if possible
     * @param item to be equipped
     * @return true if item is successfully equipped
     */
    public boolean equip(Item item){
        for(ItemType it : item.getTypes()){
            Integer freeSlots = slots.get(it);
            if(freeSlots != null && freeSlots > 0){
                freeSlots -= 1;
                slots.put(it, freeSlots);
                equippedItems.add(item);
                manager.registerEffect(item.getEffect());
                return true;
            }
        }
        return false;
    }

    public boolean remove(Item item){
        if(equippedItems.contains(item)){
            for(ItemType it : item.getTypes()){
                if(slots.containsKey(it)){
                    Integer freeSlots = slots.get(it);
                    slots.put(it, freeSlots + 1);
                }
            }
            equippedItems.remove(item);
            return true;
        } else {
            return false;
        }
    }

}
