package org.sankozi.rogueland.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for AbstractDestroyable methods
 * @author sankozi
 */
public class AbstractDestroyableTest {

    @Test
    public void testDestroyableParam() {
        AbstractDestroyable ad = new DestroyableImpl(10);
        assertEquals(ad.protection(Damage.Type.BLUNT), 0);
        assertEquals(ad.protection(Damage.Type.PIERCING), 0);
        assertEquals(ad.protection(Damage.Type.SLASHING), 0);
    }

    @Test
    public void testSetDestroyableParam() {
        AbstractDestroyable ad = new DestroyableImpl(10);
        ad.setDestroyableParam(Destroyable.Param.PIERCING_PROT, 10);
        assertEquals(ad.protection(Damage.Type.PIERCING), 10);
    }

    @Test
    public void testGetResistance() {
        AbstractDestroyable ad = new DestroyableImpl(10);
        ad.setDestroyableParam(Destroyable.Param.PIERCING_PROT, 10);
        assertEquals(ad.protection(Damage.Type.PIERCING), 10);
    }

    @Test
    public void testDamage() {
        AbstractDestroyable ad = new DestroyableImpl(10);
        assertEquals(10, ad.getDurability());
        ad.damage(0);
        assertEquals(10, ad.getDurability());
        ad.damage(1);
        assertEquals(9,  ad.getDurability());
    }

    @Test
    public void testHealFraction() {
        AbstractDestroyable ad = new DestroyableImpl(10);
        ad.damage(1);
        assertEquals(9,  ad.getDurability());
        ad.heal(1f - 0.0625f);
        assertEquals(9, ad.getDurability());
        ad.heal(0.0625f);
        assertEquals(10,  ad.getDurability());
    }

    @Test
    public void testIsDestroyed() {
        AbstractDestroyable ad = new DestroyableImpl(10);
        assertFalse(ad.isDestroyed());
        ad.damage(1);
        assertFalse(ad.isDestroyed());
        ad.damage(9);
        assertTrue(ad.isDestroyed());
    }

    public class DestroyableImpl extends AbstractDestroyable {

        public DestroyableImpl(int durability) {
            super(durability);
        }

        @Override
        public String getObjectName() {
            return "destroyable.test";
        }

        @Override
        public String getName() {
            return "Test destroyable";
        }
    }
}
