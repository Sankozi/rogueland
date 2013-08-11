package org.sankozi.rogueland;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import org.sankozi.rogueland.control.Game;
import org.sankozi.rogueland.control.GameProvider;
import org.sankozi.rogueland.control.LevelProvider;
import org.sankozi.rogueland.data.DataLoader;
import org.sankozi.rogueland.generator.LevelGenerator;
import org.sankozi.rogueland.generator.PlayerFactory;
import org.sankozi.rogueland.model.Level;
import org.sankozi.rogueland.model.Player;

/**
 *
 * @author sankozi
 */
public class RoguelandModule extends AbstractModule {

	public RoguelandModule() {
	}

    @Override
    protected void configure() {
 		//bind(Game.class).to(Game.class);
        bind(DataLoader.class).toInstance(new DataLoader());
        bind(Player.class).toProvider(PlayerFactory.class);
        bind(Level.class).toProvider(LevelProvider.class);
        bind(Game.class).toProvider(GameProvider.class);
    }
}
