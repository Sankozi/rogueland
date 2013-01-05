package org.sankozi.rogueland.gui;

import com.google.inject.Inject;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import org.apache.log4j.Logger;
import org.sankozi.rogueland.control.MessageType;
import org.sankozi.rogueland.resources.ResourceProvider;

/**
 *
 * @author sankozi
 */
public class JListLogPanel extends LogPanel{
    private final static Logger LOG = Logger.getLogger(JListLogPanel.class);
	private static final long serialVersionUID = 1L;

    private final JList<String> jlist  = new JList<>();
    private final DefaultListModel<String> list = new DefaultListModel<>();

    {
        jlist.setModel(list);
        jlist.setFont(ResourceProvider.getFont(ResourceProvider.STANDARD_FONT_NAME, 12f));
        this.add(jlist);
    }

    @Override
    public void onMessage(String message, MessageType type) {
        LOG.info("onMessage : " + message);
        list.addElement(message);
		this.repaint();
    }

	@Inject 
	void setGameSupport(GameSupport gs){
		gs.addLogListener(this);
	}

}
