package org.sankozi.rogueland.model;

/**
 * Delegate for controlling an Actor
 * @author sankozi
 */
public interface Controls {

	public Controls ALWAYS_WAIT = new AlwaysWaitControls();
	
    /**
     * Returns move for Actor
     * @return next Move
     * @throws InterruptedException
     */
    Move waitForMove() throws InterruptedException;
}

/**
 * Controls that return Move.WAIT
 * @author sankozi
 */
class AlwaysWaitControls implements Controls {
	@Override
	public Move waitForMove() throws InterruptedException {
		return Move.WAIT;
	}
}
