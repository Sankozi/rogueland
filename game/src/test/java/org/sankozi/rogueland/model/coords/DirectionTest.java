package org.sankozi.rogueland.model.coords;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * DirectionTest
 *
 * @author sankozi
 */
public class DirectionTest {
    private final static Logger LOG = LogManager.getLogger(DirectionTest.class);

    @Test
    public void testHorizontalLine(){
        assertTrue(Direction.N.isOnRightSideOf(Direction.NW));
        assertFalse(Direction.N.isOnRightSideOf(Direction.NE));

        assertTrue(Direction.S.isOnRightSideOf(Direction.E));
        assertFalse(Direction.S.isOnRightSideOf(Direction.W));

        assertTrue(Direction.NW.isOnRightSideOf(Direction.S));
        assertFalse(Direction.NW.isOnRightSideOf(Direction.SE));
    }
}
