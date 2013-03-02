package org.sankozi.rogueland.gui;

import com.google.inject.Inject;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;

import org.apache.log4j.Logger;
import org.sankozi.rogueland.control.MessageType;
import org.sankozi.rogueland.resources.ResourceProvider;

import static com.google.common.base.Preconditions.*;
import static org.sankozi.rogueland.gui.Constants.*;

/**
 *
 * @author sankozi
 */
public final class JListLogPanel extends LogPanel{
    private final static Logger LOG = Logger.getLogger(JListLogPanel.class);
	private static final long serialVersionUID = 1L;

    private final JList<String> jlist  = new JList<>();
    private final DefaultListModel<String> list = new DefaultListModel<>();

    {
        jlist.setFocusable(false);
        jlist.setModel(list);
        jlist.setFont(ResourceProvider.getFont(STANDARD_FONT_NAME, 12f));
        jlist.setOpaque(true);
        jlist.setBackground(BACKGROUND_TEXT_COLOR);
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.add(jlist, BorderLayout.CENTER);
    }

    @Override
    protected void onMessageEDT(String message, MessageType type) {
        checkState(SwingUtilities.isEventDispatchThread(), "onMessage must be called in EDT");
        LOG.info("onMessage : " + message);
        list.addElement(message);
		this.repaint();
    }

	@Inject 
	void setGameSupport(GameSupport gs){
		gs.addLogListener(this);
	}
}
