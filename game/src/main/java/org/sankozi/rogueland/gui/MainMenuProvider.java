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
    private final Action newGame;
    private final Action showInventory;

    @Inject
    public MainMenuProvider(
			@Named("exit") Action exit, 
			@Named("new-game") Action newGame,
            @Named("show-inventory") Action showInventory){
        this.exit = exit;
		this.newGame = newGame;
        this.showInventory = showInventory;
    }

    @Override
    public JMenuBar get() {
        JMenuBar menu = new JMenuBar();

        JMenu fileMenu = new JMenu();
        fileMenu.setText("File");
		fileMenu.add(newGame);
        fileMenu.add(exit);
        menu.add(fileMenu);

        JMenu gameMenu = new JMenu(); 
        gameMenu.setText("Game");
        gameMenu.add(showInventory);
        menu.add(gameMenu);
        
        return menu;
    }
}
