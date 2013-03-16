package org.sankozi.rogueland.generator;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.sankozi.rogueland.data.DataLoader;
import org.sankozi.rogueland.model.Player;

/**
 *
 * @author sankozi
 */
@Singleton
public class PlayerFactory implements Provider<Player>{

    private final DataLoader dataLoader;

    @Inject
    public PlayerFactory(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    @Override
    public Player get() {
        return new Player(dataLoader.getPlayerClass("brawler"));
    }
}
