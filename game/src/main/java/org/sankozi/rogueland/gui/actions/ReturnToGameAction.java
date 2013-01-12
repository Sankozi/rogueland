package org.sankozi.rogueland.gui.actions;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import org.sankozi.rogueland.gui.ComponentSwitcher;

/**
 *
 * @author sankozi
 */
public class ReturnToGameAction extends AbstractAction{
    private final ComponentSwitcher switcher;
    private final JComponent levelPanel;

    {
		this.putValue(Action.NAME, "Back to game");
	}

    @Inject
    ReturnToGameAction(
            ComponentSwitcher switcher, 
            @Named("level-panel") JComponent levelPanel){
        this.switcher = switcher;
        this.levelPanel = levelPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switcher.setComponent(levelPanel);
    }
}
