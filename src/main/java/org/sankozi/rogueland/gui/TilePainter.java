package org.sankozi.rogueland.gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import org.sankozi.rogueland.control.Game;
import org.sankozi.rogueland.model.Coords;

/**
 * Object that paint game field
 * @author sankozi
 */
public interface TilePainter {

	/**
	 * Paints rectangle of certain width and height
	 * @param game game object, can be unitialized, cannot be null
	 * @param g graphics object
	 * @param width width in pixels
	 * @param height height in pixels
	 */
    void paint(Game game, Graphics g, int width, int height);

	/**
	 * Returns rectangle representing tile at certain point
	 * @param game game object, can be unitialized, cannot be null
	 * @param width width in pixels
	 * @param height height in pixels
	 * @param location point in pixels
	 * @return rectangle (coords in pixels)
	 */
    Rectangle getPixelLocation(Game game, int width, int height, Coords location); 
}
