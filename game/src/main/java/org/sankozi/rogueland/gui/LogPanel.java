package org.sankozi.rogueland.gui;

import javax.swing.*;

import org.apache.log4j.Logger;
import org.sankozi.rogueland.control.LogListener;
import org.sankozi.rogueland.control.MessageType;

/**
 * Base class for Components that show game logs,
 *
 * Subclasses cannot be focusable
 *
 * @author sankozi
 */
public abstract class LogPanel extends JPanel implements LogListener{
    private final static Logger LOG = Logger.getLogger(LogPanel.class);

    {
        this.setFocusable(false);
    }

    @Override
    public final void onMessage(String message, MessageType type) {
        SwingUtilities.invokeLater(() -> onMessageEDT(message, type));
    }

    protected abstract void onMessageEDT(String message, MessageType type);
}
