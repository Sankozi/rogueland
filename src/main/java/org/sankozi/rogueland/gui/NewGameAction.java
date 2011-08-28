package org.sankozi.rogueland.gui;

import com.google.inject.Inject;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.apache.log4j.Logger;

/**
 *
 * @author Sankozi
 */
public class NewGameAction extends AbstractAction{

	private final static Logger LOG = Logger.getLogger(NewGameAction.class);
	private final static long serialVersionUID = 1L;

	private final GameSupport gameSupport;

	@Inject
	NewGameAction(GameSupport gameSupport){
		this.gameSupport = gameSupport;
	}
	
	{
		this.putValue(Action.NAME, "New game");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LOG.info("new-game");
		gameSupport.newGame();
	}
}
