package org.sankozi.rogueland.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import org.apache.log4j.Logger;
import org.sankozi.rogueland.model.Tile;

/**
 *
 * @author sankozi
 */
public class FontPainter implements TilePainter{
    private final static Logger LOG = Logger.getLogger(LevelPanel.class);
    
    Font font;
    FontMetrics metrics;

    {
        try {
            font = new Font("Monospaced", Font.BOLD, 30);
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void paint(Rectangle rect, Tile[][] tiles, Graphics g) {
        LOG.info("font painter!");
        metrics = g.getFontMetrics(font);
        g.setFont(font);
        int dy = metrics.getHeight() - 3;
        int dx = metrics.charWidth('#') - 3;

        int y = rect.y * dy;
        for(int iy = rect.y; iy < rect.height; ++iy){
            int x = rect.x * dx;
            for(int ix = rect.x; ix < rect.width; ++ix){
                switch(tiles[ix][iy].type){
                    case FLOOR:
                        g.setColor(Color.GRAY);
                        g.drawString(".", x, y);
                        break;
                    case GRASS:
                        g.setColor(Color.GREEN);
                        g.drawString("\"", x, y);
                        break;
                    case WALL:
                        g.setColor(Color.GRAY);
                        g.drawString("#", x, y);
                        break;
                }
                x += dx;
            }
            y += dy;
        }
        
    }

}
