package org.sankozi.rogueland.gui;

import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JTextPane;

/**
 * Base of implementations of renderers that uses JTextPane to 
 * render html description of object
 * @author sankozi
 */
public abstract class TextAreaRenderer<T> implements GuiRenderer<T>{
    private final JTextPane playerStats = new DescriptionTextArea();
    
    /**
     * Returns html description of passed object
     * @param object object to describe
     * @return html String containing description of rendered object
     */
    protected abstract String getHtml(T object);

    @Override
    public final void render(T object, JPanel targetPanel) {
        playerStats.setText(object == null ? "" : getHtml(object));
        Component[] components = targetPanel.getComponents();
        if(!(components.length == 1 && components[0] == playerStats)) {
            targetPanel.removeAll();
            targetPanel.setLayout(new BorderLayout());
            targetPanel.add(playerStats, BorderLayout.CENTER);
        }
    }
}
