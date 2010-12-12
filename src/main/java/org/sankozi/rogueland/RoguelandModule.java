package org.sankozi.rogueland;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import javax.swing.JMenuBar;
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
    }
}
