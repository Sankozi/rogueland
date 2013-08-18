package org.sankozi.rogueland.control;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.sankozi.rogueland.model.coords.Coords;
import static org.junit.Assert.*;

/**
 * GameControlTest
 *
 * @author sankozi
 */
public class GameControlTest extends GameTestBase {
    private final static Logger LOG = LogManager.getLogger(GameControlTest.class);

    @Test
    public void testNextTurn(){
        Game game = getSimpleGame();

        GameControl gameControl = game.getThreadGameControl();
        Coords initialPlayerCoords = game.getPlayer().getLocation();
        gameControl.nextTurn();
        assertEquals(initialPlayerCoords, game.getPlayer().getLocation());
        gameControl.nextTurn();
        assertEquals(initialPlayerCoords, game.getPlayer().getLocation());
    }
}
