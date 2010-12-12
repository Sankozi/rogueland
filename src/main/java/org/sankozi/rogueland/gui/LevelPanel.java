package org.sankozi.rogueland.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.logging.Level;
import javax.swing.JComponent;
import org.apache.log4j.Logger;

/**
 *
 * @author sankozi
 */
public class LevelPanel extends JComponent{
    private final static Logger LOG = Logger.getLogger(LevelPanel.class);

    Font font;
    FontMetrics metrics;

    {
        try {
            font = new Font("Monospaced", Font.PLAIN, 24);
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setFont(font);
        metrics = g.getFontMetrics();
        int size = metrics.getAscent() + metrics.getDescent();
//        System.out.println("FONT:" + font.getName());
        int width = metrics.charWidth('8') + 2;

        g.setColor(Color.CYAN);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);

        for(int y = 0; y < this.getHeight(); y+= size){
            for(int x=0; x < this.getWidth(); x+= width){
                g.drawChars("G".toCharArray(), 0, 1, x, y);
            }
        }
//        System.out.println("c width:" + g.getFontMetrics().charWidth('c'));
//        System.out.println("c ascend:" + g.getFontMetrics().getAscent());
//        System.out.println("c descend:" + g.getFontMetrics().getDescent());
    }
}
