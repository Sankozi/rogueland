package org.sankozi.rogueland.generator;

import com.google.common.base.Function;
import org.sankozi.rogueland.model.Item;

import java.util.Collections;

/**
 * Generates loot
 * @author sankozi
 */
public interface ItemGenerator {

    ItemGenerator NULL = new ItemGenerator() {
        @Override
        public Iterable<Item> generate(float value) {
            return Collections.emptyList();
        }
    };
//    ItemGenerator NULL = (float value) -> Collections.emptyList();

    /**
     * Creates new batch of items
     * @param value value of items to be created
     * @return new batch of items (ItemGenerator cannot return Item that already exists)
     */
    public Iterable<Item> generate(float value);

}
