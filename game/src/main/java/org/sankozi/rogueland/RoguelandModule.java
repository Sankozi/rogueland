package org.sankozi.rogueland;

import com.google.inject.AbstractModule;
import org.sankozi.rogueland.data.DataLoader;
import org.sankozi.rogueland.generator.ConstantItemGenerator;
import org.sankozi.rogueland.generator.ItemGenerator;
import org.sankozi.rogueland.generator.PlayerFactory;
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
    }
}
