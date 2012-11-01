package org.sankozi.rogueland.gui;

/**
 * Listener that is called when GameState has changed and can be read. During execution, GameState isn't changed inside
 * Game thread
 * @author sankozi
 */
public interface GameListener {

    /**
     * Event handler, must be called inside Event Dispatch Thread
     */
    void onEvent(GameEvent event);
}
