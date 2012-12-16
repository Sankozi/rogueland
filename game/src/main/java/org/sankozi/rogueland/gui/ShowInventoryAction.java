package org.sankozi.rogueland.gui;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;

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
