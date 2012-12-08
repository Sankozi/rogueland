package org.sankozi.rogueland.generator;

import com.google.common.base.Supplier;
import java.util.Collections;
import org.sankozi.rogueland.model.Item;

/**
 * Generates loot
 * @author sankozi
 */
public class ItemGenerator implements Supplier<Iterable<Item>>{

    /**
     * Creates new batch of items
     * @return 
     */
    @Override
    public Iterable<Item> get() {
        return Collections.emptyList();
    }
}
