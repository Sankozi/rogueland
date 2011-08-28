package org.sankozi.rogueland;

import com.google.inject.AbstractModule;
import org.sankozi.rogueland.control.Game;

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
    }
}
