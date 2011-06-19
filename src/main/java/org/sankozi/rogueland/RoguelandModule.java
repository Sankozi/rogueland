package org.sankozi.rogueland;

import com.google.inject.AbstractModule;
import org.sankozi.rogueland.control.Game;

/**
 *
 * @author sankozi
 */
public class RoguelandModule extends AbstractModule {

	Game game;

	public RoguelandModule(Game game) {
		this.game = game;
	}

    @Override
    protected void configure() {
 		bind(Game.class).toInstance(game);
    }
}
