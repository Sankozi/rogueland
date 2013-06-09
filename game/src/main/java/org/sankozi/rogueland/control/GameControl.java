package org.sankozi.rogueland.control;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * GameControl, object that allows to control game in certain thread
 *
 * @author sankozi
 */
public interface GameControl {

    /**
     * Makes single move
     * @return state after turn
     */
    GameState nextTurn();
}
