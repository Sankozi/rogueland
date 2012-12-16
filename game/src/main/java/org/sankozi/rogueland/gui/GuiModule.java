package org.sankozi.rogueland.gui;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
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

        bind(ComponentSwitcher.class)
                .to(MainPanel.class);

        // -------------- COMPONENTS -----------------

        bind(JComponent.class)
                .annotatedWith(Names.named("main-panel"))
                .to(MainPanel.class);

        bind(JComponent.class)
                .annotatedWith(Names.named("level-panel"))
                .toInstance(new LevelPanel());

        bind(JComponent.class)
                .annotatedWith(Names.named("inventory-panel"))
                .toInstance(new InventoryPanel());

        // ---------------- ACTIONS ------------------
		bind(Action.class)
				.annotatedWith(Names.named("new-game"))
				.to(NewGameAction.class);

        bind(Action.class)
				.annotatedWith(Names.named("show-inventory"))
				.to(ShowInventoryAction.class);
	}
}
