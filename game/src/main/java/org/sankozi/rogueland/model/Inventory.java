package org.sankozi.rogueland.model;

import com.google.common.collect.Lists;

import java.util.HashSet;

import static com.google.common.base.Preconditions.*;


/**
 * Item container
 * @author sankozi
 */
public final class Inventory {
    private final HashSet<Item> items = new HashSet<>();

    Inventory(Iterable<Item> startingEquipment) {
        items.addAll(Lists.newArrayList(startingEquipment));
    }

    public Iterable<Item> getItems(){
        return items;
    }    

    public void addItem(Item item){
        items.add(item);
    }

    public void removeItem(Item item){
        boolean removed = items.remove(item);
        checkState(removed, "item isn't available in this inventory");
    }
}
