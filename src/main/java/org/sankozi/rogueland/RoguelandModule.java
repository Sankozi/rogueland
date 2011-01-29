package org.sankozi.rogueland;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import org.sankozi.rogueland.gui.LevelPanel;
import org.sankozi.rogueland.gui.MainMenuProvider;

/**
 *
 * @author sankozi
 */
public class RoguelandModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(JMenuBar.class)
                .annotatedWith(Names.named("main-menu"))
                .toProvider(MainMenuProvider.class);

        bind(JComponent.class)
                .annotatedWith(Names.named("level-panel"))
                .to(LevelPanel.class);

        bind(JComponent.class)
                .annotatedWith(Names.named("log-panel"))
                .to(JPanel.class);
    }
}
