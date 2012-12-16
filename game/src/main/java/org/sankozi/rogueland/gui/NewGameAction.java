package org.sankozi.rogueland.gui;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import org.apache.log4j.Logger;

/**
 *
 * @author Sankozi
 */
public class NewGameAction extends AbstractAction{

	private final static Logger LOG = Logger.getLogger(NewGameAction.class);
	private final static long serialVersionUID = 1L;

	private final GameSupport gameSupport;
    private final ComponentSwitcher switcher;
    private final JComponent levelPanel;

	@Inject
	NewGameAction(GameSupport gameSupport,
            ComponentSwitcher switcher, 
            @Named("level-panel") JComponent levelPanel){
		this.gameSupport = gameSupport;
        this.switcher = switcher;
        this.levelPanel = levelPanel;
	}
	
	{
		this.putValue(Action.NAME, "New game");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LOG.info("new-game");
        this.switcher.setComponent(levelPanel);
		gameSupport.newGame();
		gameSupport.startGame();
	}
}
