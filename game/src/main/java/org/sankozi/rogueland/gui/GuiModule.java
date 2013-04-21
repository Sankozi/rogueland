package org.sankozi.rogueland.gui;

import org.sankozi.rogueland.gui.actions.ExitAction;
import org.sankozi.rogueland.gui.actions.ShowInventoryAction;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import org.apache.logging.log4j.*;
import org.sankozi.rogueland.gui.actions.ReturnToGameAction;

/**
 * TODO - make it possible to call guice outside Event Dispatch Thread
 * @author sankozi
 */
public class GuiModule extends AbstractModule{

	private final static Logger LOG = LogManager.getLogger(GuiModule.class);

	@Override
	protected void configure() {
		bind(JMenuBar.class)
                .annotatedWith(Names.named("main-menu"))
                .toProvider(MainMenuProvider.class);

        bind(LogPanel.class)
                .to(DescriptionTextAreaLogPanel.class);

        bind(ComponentSwitcher.class)
                .to(MainPanel.class);

        bind(TilePainter.class)
                .to(SquareImagePainter.class);

        // -------------- NAMED COMPONENTS -----------------

        bind(JComponent.class)
                .annotatedWith(Names.named("main-panel"))
                .to(MainPanel.class);

        bind(JComponent.class)
                .annotatedWith(Names.named("level-panel"))
                .toInstance(new LevelPanel());

        bind(JComponent.class)
                .annotatedWith(Names.named("inventory-panel"))
                .toInstance(new InventoryPanel());

        // ---------------- NAMED ACTIONS ------------------
        bind(Action.class)
                .annotatedWith(Names.named("exit"))
                .to(ExitAction.class);

		bind(Action.class)
				.annotatedWith(Names.named("new-game"))
				.to(NewGameAction.class);

        bind(Action.class)
				.annotatedWith(Names.named("show-inventory"))
				.to(ShowInventoryAction.class);

        bind(Action.class)
				.annotatedWith(Names.named("return-to-game"))
				.to(ReturnToGameAction.class);
	}
}
