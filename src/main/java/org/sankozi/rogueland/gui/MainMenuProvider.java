package org.sankozi.rogueland.gui;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

/**
 *
 * @author sankozi
 */
public class MainMenuProvider implements Provider<JMenuBar> {

    private final Action exit;

    @Inject
    public MainMenuProvider(@Named("exit") Action exit){
        this.exit = exit;
    }

    @Override
    public JMenuBar get() {
        JMenuBar menu = new JMenuBar();

        JMenu fileMenu = new JMenu();
        fileMenu.setText("File");
        fileMenu.add(exit);
        menu.add(fileMenu);

        return menu;
    }
}
