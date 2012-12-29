package org.sankozi.rogueland.model;

import java.util.HashSet;

import static com.google.inject.internal.Preconditions.*;
import org.sankozi.rogueland.data.DataLoader;

/**
 * Item container
 * @author sankozi
 */
public final class Inventory {

    private final HashSet<Item> items = new HashSet<>();

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
