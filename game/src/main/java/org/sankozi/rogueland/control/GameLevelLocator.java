package org.sankozi.rogueland.control;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sankozi.rogueland.model.*;
import org.sankozi.rogueland.model.coords.Coords;
import org.sankozi.rogueland.model.coords.CoordsSets;
import org.sankozi.rogueland.model.coords.Dim;
import org.sankozi.rogueland.model.coords.Direction;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Locator based on Level and Game instances
 * @author sankozi
 */
class GameLevelLocator implements Locator, LevelControl{
	private final static Logger LOG = LogManager.getLogger(GameLevelLocator.class);

	private final Level level;
	private final Game game;
    private final Random rand = new Random();

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
        game.addActor(actor, coords);
    }

    @Override
    public void spawnActor(Actor actor, Direction direction) {
        Coords coords = getTileFormDirection(direction);
        if(coords != null){
            spawnActor(actor, coords);
        }
    }

    private @Nullable Coords getTileFormDirection(Direction direction){
        switch(direction){
            case N:
                return getRandomFreeTile(CoordsSets.horizontalLine(0, level.getDim().width - 1, 0));
            case S:
                return getRandomFreeTile(CoordsSets.horizontalLine(0, level.getDim().width - 1, level.getDim().height));
            case W:
                return getRandomFreeTile(CoordsSets.verticalLine(0, 0, level.getDim().height - 1));
            case E:
                return getRandomFreeTile(CoordsSets.verticalLine(level.getDim().width - 1, 0, level.getDim().height - 1));
            default:
                throw new UnsupportedOperationException("unsupported direction " + direction);
        }
    }

    private @Nullable Coords getRandomFreeTile(Set<Coords> points) {
        List<Coords> freeTiles = new ArrayList<>();
        for(Coords p: points){
            if(level.getTile(p).isPassable()){
                freeTiles.add(p);
            }
        }
        return freeTiles.isEmpty()
                ? null
                : freeTiles.get(rand.nextInt(freeTiles.size()));
    }
}
