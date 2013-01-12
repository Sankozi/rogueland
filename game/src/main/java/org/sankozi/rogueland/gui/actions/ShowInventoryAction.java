package org.sankozi.rogueland.gui.actions;

import com.google.inject.Inject;
import com.google.inject.Singleton;
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
@Singleton
public class ShowInventoryAction extends AbstractAction{
    private final ComponentSwitcher switcher;
    private final JComponent inventoryPanel;

    {
		this.putValue(Action.NAME, "Inventory");
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('i'));
        this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
	}

    @Inject
    ShowInventoryAction(
            ComponentSwitcher switcher, 
            @Named("inventory-panel") JComponent inventoryPanel){
        this.switcher = switcher;
        this.inventoryPanel = inventoryPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switcher.setComponent(inventoryPanel);
    }
}
