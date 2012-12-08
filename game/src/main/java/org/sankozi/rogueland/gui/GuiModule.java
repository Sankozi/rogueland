package org.sankozi.rogueland.gui;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import javax.swing.Action;
import javax.swing.JMenuBar;
import org.apache.log4j.Logger;

/**
 *
 * @author sankozi
 */
public class GuiModule extends AbstractModule{

	private final static Logger LOG = Logger.getLogger(GuiModule.class);

	@Override
	protected void configure() {
		bind(JMenuBar.class)
                .annotatedWith(Names.named("main-menu"))
                .toProvider(MainMenuProvider.class);

        bind(LogPanel.class)
                .to(JListLogPanel.class);

        bind(LevelPanel.class)
                .annotatedWith(Names.named("level-panel"))
                .toInstance(new LevelPanel());

		bind(Action.class)
				.annotatedWith(Names.named("new-game"))
				.to(NewGameAction.class);
	}
}
