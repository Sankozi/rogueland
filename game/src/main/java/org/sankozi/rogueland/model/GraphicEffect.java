package org.sankozi.rogueland.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sankozi.rogueland.model.coords.Coords;
import org.sankozi.rogueland.model.coords.Rect;

import javax.annotation.Nullable;
import java.awt.*;

/**
 * Object containing information of effect that can be rendered but doesn't impact game in any way.
 *
 * @author sankozi
 */
public interface GraphicEffect {

    /**
     * Returns boundaries of effect in Game coords
     * @return Rect of game coordinates, cannot be null
     */
    Rect getLocation();

    /**
     * Duration of effect, in milliseconds, values returned from this method cannot change over lifetime of object
     * @return number of milliseconds
     */
    int duration();

    /**
     * Renders effect
     * @param time number of milliseconds since effect started, never higher than duration
     * @param graphics graphics2D object
     */
    void render(int time, Graphics2D graphics);
}
