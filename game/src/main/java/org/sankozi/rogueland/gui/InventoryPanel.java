package org.sankozi.rogueland.gui;

import com.google.inject.Inject;
import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import net.miginfocom.swing.MigLayout;
import oracle.jrockit.jfr.Repository;
import org.apache.log4j.Logger;
import org.sankozi.rogueland.gui.utils.Listeners;

/**
 *
 * @author sankozi
 */
public class InventoryPanel extends JPanel implements AncestorListener{
    private final static Logger LOG = Logger.getLogger(InventoryPanel.class);
    transient GameSupport gameSupport;

    private final JSplitPane contents = new JSplitPane();

    @Inject 
	void setGameSupport(GameSupport support){
        this.gameSupport = support;
	}

    {
        setLayout(new BorderLayout());
        contents.setLeftComponent(new JLabel("test-left"));
        contents.setRightComponent(new JLabel("test-right"));
        contents.setDividerLocation(50);
        add(contents, BorderLayout.CENTER);

        addAncestorListener(this);
    }

    //~onShow
    @Override
    public void ancestorAdded(AncestorEvent event) {
        LOG.info("ancestorListener");
        repaint();
    }

    @Override
    public void ancestorRemoved(AncestorEvent event) {
    }

    @Override
    public void ancestorMoved(AncestorEvent event) {
    }
}
