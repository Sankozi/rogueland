package org.sankozi.rogueland.model;

import com.google.common.collect.Sets;
import com.google.inject.internal.ImmutableMap;
import java.util.EnumMap;
import java.util.Set;
import org.apache.log4j.Logger;
import org.sankozi.rogueland.model.effect.Effect;
import org.sankozi.rogueland.model.effect.EffectManager;

/**
 *
 * @author sankozi
 */
public final class EquippedItems {
    private final static Logger LOG = Logger.getLogger(EquippedItems.class);
    /** number of items that can be equipped */
    private final EnumMap<ItemType, Integer> slots = new EnumMap<>(ImmutableMap.<ItemType, Integer>builder()
                .put(ItemType.HELD, 1)
                .put(ItemType.WORN_AROUND_NECK, 1)
                .put(ItemType.WORN_AROUND_WAIST, 1)
                .put(ItemType.WORN_CHEST, 1)
                .put(ItemType.WORN_FEET, 1)
                .put(ItemType.WORN_FINGER, 2)
                .put(ItemType.WORN_LEGS, 1)
                .build());

    private final Set<Item> equippedItems = Sets.newHashSetWithExpectedSize(slots.size());

    private final Player player;
    private final Inventory equipment;

    public EquippedItems(Player player, Inventory equipment) {
        this.player = player;
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
                player.getEffectManager().registerEffect(item.getUsedEffect());
                if(it == ItemType.HELD){
                    player.setWeaponEffect(item.getWeaponEffect());
                }
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
                if(it == ItemType.HELD){
                    player.setWeaponEffect(Effect.NULL);
                }
            }
            player.getEffectManager().removeEffect(item.getUsedEffect());
            equippedItems.remove(item);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return all items that are equipped
     * @return 
     */
    public Set<Item> getEquippedItems(){
        return equippedItems;
    }

    /**
     * Return all items that are not equpped
     * @return 
     */
    public Set<Item> getUnequippedItems(){
        Set<Item> ret = Sets.newHashSet(equipment.getItems());
        ret.removeAll(equippedItems);
        return ret;
    }
}
