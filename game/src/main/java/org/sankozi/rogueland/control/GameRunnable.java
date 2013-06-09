package org.sankozi.rogueland.control;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

/**
 * GameRunnable
 *
 * @author sankozi
 */
final class GameRunnable implements Runnable {
    private final static Logger LOG = LogManager.getLogger(GameRunnable.class);

    final Supplier<GameControl> gameControlSupplier;

    GameRunnable(Supplier<GameControl> gameControlSupplier) {
        this.gameControlSupplier = gameControlSupplier;
    }

    @Override
    public void run() {
        try {
            GameControl gameControl = gameControlSupplier.get();
            GameState state;
            do {
                state = gameControl.nextTurn();
            } while (state != GameState.END);
        } catch (IllegalStateException ex){
            if(ex.getCause() instanceof InterruptedException){
                LOG.info("game ended");
            }
        }
    }
}
