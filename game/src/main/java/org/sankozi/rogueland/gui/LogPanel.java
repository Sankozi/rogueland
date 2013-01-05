package org.sankozi.rogueland.gui;

import javax.swing.JPanel;
import org.apache.log4j.Logger;
import org.sankozi.rogueland.control.LogListener;

/**
 * Base class for Components that show game logs
 * @author sankozi
 */
public abstract class LogPanel extends JPanel implements LogListener{
    private final static Logger LOG = Logger.getLogger(LogPanel.class);
}
