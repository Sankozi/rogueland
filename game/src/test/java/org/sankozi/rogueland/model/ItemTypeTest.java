package org.sankozi.rogueland.model;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author sankozi
 */
public class ItemTypeTest {
    
    @Test
    public void testExpandSingle() {
        Set<ItemType> expanded = ItemType.expand(EnumSet.of(ItemType.SWORD));
        assertThat(expanded, hasItems(ItemType.SWORD, ItemType.WEAPON, ItemType.HELD));
    }

    @Test
    public void testExpandMultiple() {
        Set<ItemType> expanded = ItemType.expand(EnumSet.of(ItemType.SWORD, ItemType.MACE));
        assertThat(expanded, hasItems(ItemType.SWORD, ItemType.WEAPON, ItemType.HELD, ItemType.MACE));
    }
}
