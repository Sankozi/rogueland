package org.sankozi.rogueland.gui;

import com.google.inject.Inject;
import java.awt.BorderLayout;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.ListModel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListDataListener;
import net.miginfocom.swing.MigLayout;
import oracle.jrockit.jfr.Repository;
import org.apache.log4j.Logger;
import org.sankozi.rogueland.gui.utils.Listeners;
import org.sankozi.rogueland.model.Item;

/**
 *
 * @author sankozi
 */
public class InventoryPanel extends JPanel implements AncestorListener{
    private final static Logger LOG = Logger.getLogger(InventoryPanel.class);
    transient GameSupport gameSupport;

    private final JSplitPane contents = new JSplitPane();

    private final JList itemList = new JList();
    private final DefaultListModel itemsDataModel = new DefaultListModel();

    @Inject 
	void setGameSupport(GameSupport support){
        this.gameSupport = support;
	}

    {
        setLayout(new BorderLayout());
        contents.setLeftComponent(itemList);
        contents.setRightComponent(new JLabel("test-right"));
        contents.setDividerLocation(50);
        add(contents, BorderLayout.CENTER);

        addAncestorListener(this);
    }

    //~onShow
    @Override
    public void ancestorAdded(AncestorEvent event) {
        LOG.info("ancestorListener");
        itemsDataModel.clear();
        for(Item item : gameSupport.getGame().getPlayer().getEquipment().getItems()){
            itemsDataModel.addElement(item.getObjectName());
        }
        repaint();
    }

    @Override
    public void ancestorRemoved(AncestorEvent event) {
    }

    @Override
    public void ancestorMoved(AncestorEvent event) {
    }
}
