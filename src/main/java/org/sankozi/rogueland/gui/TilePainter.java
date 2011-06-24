package org.sankozi.rogueland.gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import org.sankozi.rogueland.model.Level;

/**
 *
 * @author sankozi
 */
public interface TilePainter {

    void paint(Level level, Graphics g);

    Rectangle getPixelLocation(Rectangle rect, Point location);
}
