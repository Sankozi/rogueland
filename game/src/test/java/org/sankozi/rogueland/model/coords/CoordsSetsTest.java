package org.sankozi.rogueland.model.coords;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * CoordsSetsTest
 *
 * @author sankozi
 */
public class CoordsSetsTest {

    @Test
    public void testHorizontalLine(){
        Set<Coords> coords = CoordsSets.horizontalLine(0, 2, 1);
        assertThat(coords, containsInAnyOrder(new Coords(0, 1), new Coords(1, 1), new Coords(2, 1)));

        coords = CoordsSets.horizontalLine(0, 1, 1);
        assertThat(coords, containsInAnyOrder(new Coords(0, 1), new Coords(1, 1)));

        Set<Coords> copy = Sets.newHashSet(coords);
        assertEquals(coords, copy);
        assertEquals(copy, coords);
    }

    @Test
    public void testVerticalLine(){
        Set<Coords> coords = CoordsSets.verticalLine(0, 1, 2);
        assertThat(coords, containsInAnyOrder(new Coords(0, 1), new Coords(0, 2)));

        coords = CoordsSets.verticalLine(5, 5, 6);
        assertThat(coords, containsInAnyOrder(new Coords(5, 5), new Coords(5, 6)));

        Set<Coords> copy = Sets.newHashSet(coords);
        assertEquals(coords, copy);
        assertEquals(copy, coords);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidHorizontalLine1(){
        Set<Coords> coords = CoordsSets.horizontalLine(0, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidHorizontalLine2(){
        Set<Coords> coords = CoordsSets.horizontalLine(6, -1, 8);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidVerticalLine1(){
        Set<Coords> coords = CoordsSets.verticalLine(0, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidVerticalLine2(){
        Set<Coords> coords = CoordsSets.verticalLine(0, 5, -1);
    }

    @Test
    public void testRectangle(){
        Set<Coords> coords = CoordsSets.rectangle(1, 1, 3, 3);

        assertEquals(9, coords.size());
        assertThat(coords, hasItems(new Coords(1, 1), new Coords(3, 2), new Coords(3, 3)));
        assertThat(coords, not(hasItems(new Coords(0, 1), new Coords(4, 2))));

        Set<Coords> copy = Sets.newHashSet(coords);

        assertEquals(coords, copy);
        assertEquals(copy, coords);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidRectangle1(){
        Set<Coords> coords = CoordsSets.rectangle(0, 5, 1, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidRectangle(){
        Set<Coords> coords = CoordsSets.rectangle(0, 5, 0, 6);
    }

}
