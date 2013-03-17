package org.sankozi.rogueland.gui;

import java.awt.Graphics;
import java.awt.Rectangle;
import org.sankozi.rogueland.control.Game;
import org.sankozi.rogueland.model.Coords;
import org.sankozi.rogueland.model.Tile;

import javax.annotation.Nullable;

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
	 * Returns rectangle containing pixel coordinates of a certain tile
	 * @param game game object, can be unitialized, cannot be null
	 * @param width width in pixels
	 * @param height height in pixels
	 * @param location game coordinates
	 * @return rectangle (coords in pixels)
	 */
    Rectangle getTileRectangle(Game game, int width, int height, Coords location);

    /**
     * Returns tile at certain pixel location
     * @param game game object, can be unitialized, cannot be null
     * @param width width in pixels
     * @param height height in pixels
     * @param location point in pixels
     * @return Tile can be null if coordinates point to empty space
     */
    @Nullable
    Tile getTilePixelLocation(Game game, int width, int height, Coords location);
}
