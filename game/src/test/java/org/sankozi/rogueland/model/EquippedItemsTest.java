package org.sankozi.rogueland.model;

import java.util.Collections;
import org.junit.Test;
import org.sankozi.rogueland.model.effect.EffectManager;

import static org.junit.Assert.*;
import static org.sankozi.rogueland.model.TestUtils.*;

/**
 *
 * @author sankozi
 */
public class EquippedItemsTest {
    @Test
    public void testEquipRemove() {
        Player p = new Player();
        p.setControls(Controls.ALWAYS_WAIT);
        EffectManager em = EffectManager.forPlayer(p);
        EquippedItems items = new EquippedItems(p, new Inventory(Collections.<Item>emptyList()));
        Item sword = createItem("test-sword", ItemType.SWORD);
        assertTrue("sword can be equipped at start", items.equip(sword));
        assertTrue("sword can be removed", items.remove(sword));

        Item armor = createItem("test-armor", ItemType.WORN_CHEST);
        assertTrue(items.equip(armor));
        assertTrue(items.equip(sword));

    }
}
