package org.sankozi.rogueland.control;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sankozi.rogueland.model.*;
import org.sankozi.rogueland.model.coords.Coords;
import org.sankozi.rogueland.model.coords.Dim;
import org.sankozi.rogueland.model.coords.Direction;

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
	public Coords getPlayerLocation() {
		return game.getPlayer().getLocation();
	}

    @Override
    public Dim getLevelDim() {
        return level.getDim();
    }

    @Override
    public void spawnActor(Actor actor, Coords coords) {

    }

    @Override
    public void spawnActor(Actor actor, Direction direction) {
        switch(direction){

        }
    }

    private Coords findRandomFreeTile() {

    }
}
