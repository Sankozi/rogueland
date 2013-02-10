package org.sankozi.rogueland.gui.render;

import javax.annotation.Nullable;
import javax.swing.JPanel;

/**
 * Stateful object that renders instances of certain type using 
 * JPanel - filling it with components showing state 
 * of passed object
 * @author sankozi
 */
public interface GuiRenderer<T>  {
    /**
     * Renders object on targetPanel, this method must be called in EDT,
     * This method can change contents of passed JPanel in any way
     * @param object object to render
     * @param targetPanel panel used for rendering
     */
    void render(@Nullable T object, JPanel targetPanel);
}
