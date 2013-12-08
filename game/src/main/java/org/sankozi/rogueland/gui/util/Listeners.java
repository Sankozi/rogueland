package org.sankozi.rogueland.gui.util;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import org.apache.logging.log4j.*;


/**
 *
 * @author sankozi
 */
public final class Listeners {
    private final static Logger LOG = LogManager.getLogger(Listeners.class);

    private static ComponentListener LOGGING_COMPONENT_LISTENER;
    private static AncestorListener LOGGING_ANCESTOR_LISTENER;

    public static AncestorListener loggingAncestorListener() {
        if(LOGGING_ANCESTOR_LISTENER == null){
            LOGGING_ANCESTOR_LISTENER = new AncestorListener(){
                public void ancestorAdded(AncestorEvent e) {
                    LOG.debug(e.getComponent().getClass().getName() + " : ancestorAdded");
                }
                public void ancestorRemoved(AncestorEvent e) {
                    LOG.debug(e.getComponent().getClass().getName() + " : ancestorRemoved");
                }
                public void ancestorMoved(AncestorEvent e) {
                    LOG.debug(e.getComponent().getClass().getName() + " : ancestorMoved");
                }
            };
        }
        return LOGGING_ANCESTOR_LISTENER;
    }

    public static ComponentListener loggingComponentListener(){
        if(LOGGING_COMPONENT_LISTENER == null){
            LOGGING_COMPONENT_LISTENER = new ComponentListener(){
                public void componentResized(ComponentEvent e) {
                    LOG.debug(e.getComponent().getClass().getName() + " : componentResized");
                }
                public void componentMoved(ComponentEvent e) {
                    LOG.debug(e.getComponent().getClass().getName() + "componentMoved");
                }
                public void componentShown(ComponentEvent e) {
                    LOG.debug(e.getComponent().getClass().getName() + "componentShown");
                }
                public void componentHidden(ComponentEvent e) {
                    LOG.debug(e.getComponent().getClass().getName() + "componentHidden");
                }
            };
        }
        return LOGGING_COMPONENT_LISTENER;
    }
    
    
    private Listeners() {}
}
