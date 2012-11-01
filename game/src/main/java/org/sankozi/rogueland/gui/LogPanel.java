package org.sankozi.rogueland.gui;

import javax.swing.JPanel;
import org.apache.log4j.Logger;
import org.sankozi.rogueland.control.LogListener;

/**
 * Base class for LoggingPanels
 * @author sankozi
 */
public abstract class LogPanel extends JPanel implements LogListener{
    private final static Logger LOG = Logger.getLogger(LogPanel.class);

//    @Override
//    public void onMessage(String message, MessageType type) {
//        LOG.info("onMessage:" + message);
//    }
}
