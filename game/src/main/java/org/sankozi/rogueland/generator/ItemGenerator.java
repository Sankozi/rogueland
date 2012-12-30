package org.sankozi.rogueland.generator;

import com.google.common.base.Function;
import org.sankozi.rogueland.model.Item;

/**
 * Generates loot
 * @author sankozi
 */
public interface ItemGenerator extends Function<Float, Iterable<Item>> {

    /**
     * Creates new batch of items
     * @param luck luck bonus - may affect quality of generated items
     * @return new batch of items
     */
    @Override
    public Iterable<Item> apply(Float luck);
}
