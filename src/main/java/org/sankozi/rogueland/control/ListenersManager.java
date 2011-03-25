package org.sankozi.rogueland.control;

import com.google.common.collect.Lists;
import java.util.List;

/**
 *
 * @author sankozi
 */
class ListenersManager {

    private final List<GameListener> listeners = Lists.newArrayList();

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
}
