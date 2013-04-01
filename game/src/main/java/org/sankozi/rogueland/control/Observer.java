package org.sankozi.rogueland.control;

import org.sankozi.rogueland.control.LevelControl;
import org.sankozi.rogueland.control.Locator;

/**
 * Object that observes game and can change its state
 *
 * Each observer can be attached to single game only
 *
 * @Author sankozi
 **/
public interface Observer {

    /**
     * Method called after attaching to a level, its called only once
     * Method is called only in game thread
     * @param level level locator
     * @param control level control
     */
    public void attach(Locator level, LevelControl control);

    /**
     * Method called after each turn, its called after move of every actor
     * Method is called only in game thread
     * @param level level locator
     * @param control level control
     */
    public void tick(Locator level, LevelControl control);
}
