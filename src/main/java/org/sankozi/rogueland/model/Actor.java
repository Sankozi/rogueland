package org.sankozi.rogueland.model;

import java.awt.Point;

/**
 *
 * @author sankozi
 */
public interface Actor {
    Move act(Level input);

    Point getLocation();
    void setLocation(Point point);

}
