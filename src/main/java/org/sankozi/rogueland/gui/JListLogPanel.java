package org.sankozi.rogueland.gui;

import org.apache.log4j.Logger;
import org.sankozi.rogueland.control.MessageType;

/**
 *
 * @author sankozi
 */
public class JListLogPanel extends LogPanel{
    private final static Logger LOG = Logger.getLogger(JListLogPanel.class);

    @Override
    public void onMessage(String message, MessageType type) {
        LOG.info("onMessage : " + message);
    }

}
