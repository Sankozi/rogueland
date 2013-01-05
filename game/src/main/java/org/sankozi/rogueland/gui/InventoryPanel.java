package org.sankozi.rogueland.gui;

import com.google.inject.Inject;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.log4j.Logger;
import org.sankozi.rogueland.model.Item;

/**
 *
 * @author sankozi
 */
public class InventoryPanel extends JPanel implements AncestorListener, ListSelectionListener{
    private final static Logger LOG = Logger.getLogger(InventoryPanel.class);
    transient GameSupport gameSupport;

    private final JSplitPane contents = new JSplitPane();

    private final DefaultListModel itemsDataModel = new DefaultListModel();
    private final JList itemList = new JList(itemsDataModel);
    private final JTextPane itemDescription = new JTextPane();

    @Inject 
	void setGameSupport(GameSupport support){
        this.gameSupport = support;
	}

    {
        itemList.addListSelectionListener(this);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.setCellRenderer(new ItemListRenderer());

        itemDescription.setEditable(false);
        
        setLayout(new BorderLayout());
        contents.setLeftComponent(itemList);
        contents.setRightComponent(itemDescription);
        contents.setDividerLocation(150);
        add(contents, BorderLayout.CENTER);

        addAncestorListener(this);
    }

    private static class ItemListRenderer implements ListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            return new JLabel(((Item)value).getName());
        }
    }

    //~onShow
    @Override
    public void ancestorAdded(AncestorEvent event) {
        LOG.info("ancestorListener");
        itemsDataModel.clear();
        for(Item item : gameSupport.getGame().getPlayer().getEquipment().getItems()){
            itemsDataModel.addElement(item);
        }
        repaint();
    }

    @Override
    public void ancestorRemoved(AncestorEvent event) {
    }

    @Override
    public void ancestorMoved(AncestorEvent event) {
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        Item item = (Item) itemList.getSelectedValue();
        itemDescription.setText(item.getDescription());
    }
}
