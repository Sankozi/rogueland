package org.sankozi.rogueland.control;

/**
 * Listener awaiting for Game log messages
 * @author sankozi
 */
public interface LogListener {

    /**
     * Method that is called when message arrives
     * @param message content of the message, never null
     * @param type type of the message, nevel null
     */
    void onMessage(String message, MessageType type);

}
