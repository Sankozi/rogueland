package org.sankozi.rogueland.control;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sankozi.rogueland.model.Level;
import org.sankozi.rogueland.model.Player;
import org.sankozi.rogueland.model.coords.Coords;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * GameProvider
 *
 * @author sankozi
 */
public class GameProvider implements Provider<Game> {
    private final static Logger LOG = LogManager.getLogger(GameProvider.class);

    private Provider<Player> playerProvider;
    private Provider<Level> levelProvider;

    @Override
    public Game get() {
        Player player = playerProvider.get();
        player.setLocation(new Coords(2,2));
        return new Game(player, levelProvider.get());
    }

    public Provider<Player> getPlayerProvider() {
        return playerProvider;
    }

    @Inject
    public void setPlayerProvider(Provider<Player> playerProvider) {
        this.playerProvider = playerProvider;
    }

    public Provider<Level> getLevelProvider() {
        return levelProvider;
    }

    @Inject
    public void setLevelProvider(Provider<Level> levelProvider) {
        this.levelProvider = levelProvider;
    }
}
