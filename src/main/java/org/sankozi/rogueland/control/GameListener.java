package org.sankozi.rogueland.control;

/**
 * Listener that is called inside Event Dispatch Thread. During execution, GameState isn't changed inside
 * Game thread
 * @author sankozi
 */
public interface GameListener {

    /**
     * Event handler, must be called inside Event Dispatch Thread
     */
    void onEvent(GameEvent event);
}
