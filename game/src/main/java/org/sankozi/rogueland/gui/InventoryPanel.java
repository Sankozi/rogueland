package org.sankozi.rogueland.gui;

import com.google.common.base.Throwables;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.Action;
import javax.swing.BorderFactory;
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
import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTMLDocument;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.Logger;
import org.sankozi.rogueland.model.EquippedItems;
import org.sankozi.rogueland.model.Item;
import org.sankozi.rogueland.model.Player;
import org.sankozi.rogueland.resources.ResourceProvider;

/**
 *
 * @author sankozi
 */
public class InventoryPanel extends JPanel implements AncestorListener, ListSelectionListener, KeyListener{
    private final static Logger LOG = Logger.getLogger(InventoryPanel.class);
    transient GameSupport gameSupport;
    transient private Action returnToGame;

    private final JSplitPane contents = new JSplitPane();

    private final DefaultListModel<ItemDto> itemsDataModel = new DefaultListModel<>();
    private final JList itemList = new JList(itemsDataModel);

    /** panel with stats and item description */
    private final JPanel statsPanel = new JPanel();
    private final JTextPane itemDescription = new JTextPane();
    private final JPanel playerDescription = new JPanel();
    private final PlayerStatsRenderer playerRenderer = new PlayerStatsRenderer();

    @Inject 
	void setGameSupport(GameSupport support){
        this.gameSupport = support;
	}

    @Inject
    public void setReturnToGame(@Named("return-to-game") Action returnToGame) {
        this.returnToGame = returnToGame;
    }

    {
        itemList.addListSelectionListener(this);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.setCellRenderer(new ItemListRenderer());
        
        initStatsPanel();
        initItemDescription();
        
        setLayout(new BorderLayout());
        contents.setLeftComponent(itemList);
        contents.setRightComponent(statsPanel);
        contents.setDividerLocation(150);
        add(contents, BorderLayout.CENTER);

        addAncestorListener(this);
        itemList.addKeyListener(this);
        itemList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    ItemDto item = itemsDataModel.get(index);
                    Player player = gameSupport.getGame().getPlayer();
                    if(item.equipped){
                        if(player.getEquippedItems().remove(item.item)){
                            item.equipped = false;
                            playerDescription.removeAll();
                            playerDescription.add(playerRenderer.renderPlayerStats(player), BorderLayout.CENTER);
                        }
                    } else {
                        if(player.getEquippedItems().equip(item.item)){
                            item.equipped = true;
                            playerDescription.removeAll();
                            playerDescription.add(playerRenderer.renderPlayerStats(player), BorderLayout.CENTER);
                        }
                    }
                    list.repaint();
                } 
            }
        });
        itemDescription.addKeyListener(this);
        addKeyListener(this);
    }

    MutableAttributeSet attrs;
    Font font = ResourceProvider.getFont(Constants.STANDARD_FONT_NAME, 14f);

    private void initItemDescription() {
        itemDescription.setEditable(false);
        itemDescription.setContentType("text/html");
        itemDescription.setDocument(new CustomFontStyledDocument());
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            returnToGame.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
        }
    }

    private void initStatsPanel() {
        statsPanel.setLayout(new MigLayout("fill, wrap 1", "[left, fill]","[50%, top][50%, top]"));
        statsPanel.add(itemDescription, "grow");
        statsPanel.add(playerDescription, "grow");
        playerDescription.setLayout(new BorderLayout());
    }

    private class CustomFontStyledDocument extends HTMLDocument{

        @Override
        public Font getFont(AttributeSet attr) {
            return font;
        }
    }

    private class ItemListRenderer implements ListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            ItemDto item = (ItemDto) value;
            JLabel ret = new JLabel(item.item.getName());
            ret.setFont(font);
            if(isSelected){
                ret.setOpaque(true);
                if(item.equipped){
                    ret.setBackground(Constants.BACKGROUND_EMPH_TEXT_COLOR);
                } else {
                    ret.setBackground(Constants.BACKGROUND_TEXT_COLOR);
                }
                ret.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, false));
            } else if(item.equipped){
                ret.setOpaque(true);
                ret.setBackground(Constants.BACKGROUND_EMPH_TEXT_COLOR);
            }
            return ret;
        }
    }

    private final static class ItemDto {
        private final Item item;
        private boolean equipped;

        ItemDto(Item item, boolean wasEquipped) {
            this.item = item;
            this.equipped = wasEquipped;
        }
    }

    //~onShow
    @Override
    public void ancestorAdded(AncestorEvent event) {
        LOG.info("ancestorListener");
        itemsDataModel.clear();
        EquippedItems equippedItems = gameSupport.getGame().getPlayer().getEquippedItems();
        for(Item item : equippedItems.getEquippedItems()){
            itemsDataModel.addElement(new ItemDto(item, true));
        }
        for(Item item : equippedItems.getUnequippedItems()){
            itemsDataModel.addElement(new ItemDto(item, false));
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
        ItemDto item = (ItemDto) itemList.getSelectedValue();
        if(item != null){
            itemDescription.setText(item.item.getDescription().replace("\n", "<br/>"));
        }
    }
}
