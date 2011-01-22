package org.sankozi.rogueland.control;

import java.util.ArrayList;
import java.util.List;

/**
 * Logging mechanism for Game events
 * @author sankozi
 */
public class GameLog {
    /** GameLog attached to this game thread */
    private final static ThreadLocal<GameLog> threadLog = new ThreadLocal<GameLog>();

    private final List<LogListener> listeners = new ArrayList<LogListener>();

    public void addListener(LogListener listener) {
        if(!listeners.contains(listener)){
            listeners.add(listener);
        }
    }

    public void log(String message, MessageType type){
        for(LogListener listener: listeners){
            listener.onMessage(message, type);
        }
    }

    void setThreadLog(GameLog log){
        threadLog.set(log);
    }
}
