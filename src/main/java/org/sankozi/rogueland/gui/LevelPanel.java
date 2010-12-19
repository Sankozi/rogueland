package org.sankozi.rogueland.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;
import org.apache.log4j.Logger;
import org.sankozi.rogueland.model.Level;

/**
 *
 * @author sankozi
 */
public class LevelPanel extends JComponent{
    private final static Logger LOG = Logger.getLogger(LevelPanel.class);

    

    Level level = new Level();
    TilePainter tilePainter = new FontPainter();
    Rectangle levelSize = new Rectangle(0, 0, Level.WIDTH, Level.HEIGHT);

    

    @Override
    public void paint(Graphics g) {
        tilePainter.paint(levelSize, level.getTiles(), g);

//        g.setFont(font);
//        metrics = g.getFontMetrics();
//        int size = metrics.getAscent() + metrics.getDescent();
////        System.out.println("FONT:" + font.getName());
//        int width = metrics.charWidth('8') + 2;
//
//        g.setColor(Color.CYAN);
//        g.fillRect(0, 0, this.getWidth(), this.getHeight());
//        g.setColor(Color.BLACK);
//
//        for(int y = 0; y < this.getHeight(); y+= size){
//            for(int x=0; x < this.getWidth(); x+= width){
//                g.drawChars("G".toCharArray(), 0, 1, x, y);
//            }
//        }
//        System.out.println("c width:" + g.getFontMetrics().charWidth('c'));
//        System.out.println("c ascend:" + g.getFontMetrics().getAscent());
//        System.out.println("c descend:" + g.getFontMetrics().getDescent());
    }
}
