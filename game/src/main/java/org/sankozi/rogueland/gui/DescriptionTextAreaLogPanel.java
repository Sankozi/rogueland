package org.sankozi.rogueland.gui;

import org.apache.logging.log4j.*;
import org.sankozi.rogueland.control.MessageType;

import javax.swing.*;

import java.awt.*;

import static com.google.common.base.Preconditions.*;

/**
 * DescriptionTextAreaLogPanel
 *
 * @author sankozi
 */
public final class DescriptionTextAreaLogPanel extends LogPanel{
    private final static Logger LOG = LogManager.getLogger(DescriptionTextAreaLogPanel.class);

    private final DescriptionTextArea log = new DescriptionTextArea();
    private final StringBuilder sb = new StringBuilder(255).append("<html>");

    private final static String ENDING = "</html>";

    {
        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(log), BorderLayout.CENTER);
        log.setOpaque(false);
    }

    @Override
    protected void onMessageEDT(String message, MessageType type) {
        LOG.info("onMessageEDT");
        checkState(SwingUtilities.isEventDispatchThread(), "onMessage must be called in EDT");
        sb.append(message).append("<br/>").append("</html>");
        log.setText(sb.toString());
        sb.delete(sb.length() - ENDING.length(), sb.length());
        log.repaint();
    }
}
