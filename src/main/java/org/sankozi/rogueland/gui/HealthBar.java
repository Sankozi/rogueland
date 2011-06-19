package org.sankozi.rogueland.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 * Component for repesenting health bar
 * @author sankozi
 */
public class HealthBar extends JComponent{
	private static final long serialVersionUID = 1L;

    {
        setPreferredSize(new Dimension(100, 20));
    }

    @Override
    public void paint(Graphics g) {
        
    }
}
