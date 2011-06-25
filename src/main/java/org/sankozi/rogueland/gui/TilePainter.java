package org.sankozi.rogueland.gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import org.sankozi.rogueland.control.Game;

/**
 *
 * @author sankozi
 */
public interface TilePainter {

    void paint(Game game, Graphics g, int width, int height);

    Rectangle getPixelLocation(Game game, int width, int height, Point location); 
}
