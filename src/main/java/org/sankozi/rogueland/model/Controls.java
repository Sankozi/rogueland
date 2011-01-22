package org.sankozi.rogueland.model;

/**
 * Delegate for controlling an Actor
 * @author sankozi
 */
public interface Controls {

    /**
     * Returns move for Actor
     * @return next Move
     * @throws InterruptedException
     */
    Move waitForMove() throws InterruptedException;

}
