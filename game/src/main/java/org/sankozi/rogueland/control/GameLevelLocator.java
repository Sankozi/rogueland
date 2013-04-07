package org.sankozi.rogueland.control;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sankozi.rogueland.model.*;

/**
 * Locator based on Level and Game instances
 * @author sankozi
 */
class GameLevelLocator implements Locator, LevelControl{
	private final static Logger LOG = LogManager.getLogger(GameLevelLocator.class);

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

    @Override
    public void spawnActor(Actor actor, Coords coords) {

    }
}
