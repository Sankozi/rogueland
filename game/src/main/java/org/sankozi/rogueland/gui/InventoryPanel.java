package org.sankozi.rogueland.gui;

import com.google.inject.Inject;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

/**
 *
 * @author sankozi
 */
public class InventoryPanel extends JPanel{
    private final static Logger LOG = Logger.getLogger(InventoryPanel.class);
    transient GameSupport gameSupport;

    @Inject 
	void setGameSupport(GameSupport support){
        this.gameSupport = support;
	}
}
