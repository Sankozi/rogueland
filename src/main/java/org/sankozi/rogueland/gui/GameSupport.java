package org.sankozi.rogueland.gui;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.util.List;
import org.sankozi.rogueland.control.Game;
import org.sankozi.rogueland.model.Controls;
import org.sankozi.rogueland.model.Move;

/**
 * Class supporting interaction between GameState and GUI
 * @author sankozi
 */
class GameSupport {

    private final List<GameListener> listeners = Lists.newArrayList();

    private final Game game;
    private final GameEvent gameEvent;

    public GameSupport(Game game, Controls controls) {
        Preconditions.checkNotNull(game, "game cannot be null");
        this.game = game;
        this.gameEvent = new GameEvent(game);
        this.game.setControls(new SynchronizedControls(controls));
    }

    public void add(GameListener listener){
        listeners.add(listener);
    }

    public void remove(GameListener listener){
        listeners.remove(listener);
    }

    public void clear(){
        this.listeners.clear();
    }

    public void fireGameEvent(GameEvent ge){
        for(GameListener listener : listeners){
            listener.onEvent(ge);
        }
    }

    private class SynchronizedControls implements Controls {

        private final Controls base;

        public SynchronizedControls(Controls base) {
            this.base = base;
        }

        @Override
        public Move waitForMove() throws InterruptedException {
            for(GameListener gl: listeners){
                gl.onEvent(gameEvent);
            }
            return base.waitForMove();
        }
    }
}
