package org.sankozi.rogueland;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for MathUtils
 * @author sankozi
 */
public class MathUtilsTest {
    
    public MathUtilsTest() {
    }  

    /**
     * Test of clamp method, of class MathUtils.
     */
    @Test
    public void testClamp() {
        assertEquals(0, MathUtils.clamp(0, 0, 0));        
        assertEquals(0, MathUtils.clamp(0, 0, 5));  
        assertEquals(0, MathUtils.clamp(-5, 0, 5));
        
        assertEquals(0, MathUtils.clamp(500, -5, 0));        
        assertEquals(5, MathUtils.clamp(500, -5, 5));
        
        assertEquals(5, MathUtils.clamp(5, -50, 50));
    }
}
