package org.sankozi.rogueland.control;

import java.awt.Point;
import org.apache.log4j.Logger;
import org.sankozi.rogueland.model.Coords;
import org.sankozi.rogueland.model.Level;
import org.sankozi.rogueland.model.Locator;

/**
 * Locator based on Level and Game instances
 * @author Michał Sankowski
 */
class GameLevelLocator implements Locator{
	private final static Logger LOG = Logger.getLogger(GameLevelLocator.class);

	private final Level level;
	private final Game game;

	public GameLevelLocator(Game game, Level level) {
		this.level = level;
		this.game = game;
	}

	@Override
	public Coords playerLocation() {
		return game.getPlayer().getLocation();
	}
}
