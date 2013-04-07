package org.sankozi.rogueland.control;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.*;

import javax.swing.*;

/**
 * Logging mechanism for Game events
 * @author sankozi
 */
public class GameLog {
    private final static Logger LOG = LogManager.getLogger(GameLog.class);
    /** GameLog attached to this game thread */
    private final static ThreadLocal<GameLog> threadLog = new ThreadLocal<>();

    private final List<LogListener> listeners = new ArrayList<>();

    public static void info(String message){
        threadLog.get().log(message, MessageType.INFO);
    }

    public void addListener(LogListener listener) {
        LOG.info("adding listener");
        if(!listeners.contains(listener)){
            listeners.add(listener);
        }
    }

    public void log(String message, MessageType type){
        for(LogListener listener: listeners){
            listener.onMessage(message, type);
        }
    }

    static void initThreadLog(GameLog log){
        Preconditions.checkNotNull(log, "game log cannot be null");
        threadLog.set(log);
        LOG.info("game logger initialized");
    }
}
