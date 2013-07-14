package org.sankozi.rogueland;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
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

    private final static Provider<Level> LEVEL_PROVIDER = () -> {
        Level ret = new Level(200,200);
        LevelGenerator.generate(ret);
        return ret;
    };

	public RoguelandModule() {
	}

    @Override
    protected void configure() {
 		//bind(Game.class).to(Game.class);
        bind(DataLoader.class).toInstance(new DataLoader());
        bind(Player.class).toProvider(PlayerFactory.class);
        bind(Level.class).toProvider(LEVEL_PROVIDER);
    }
}
