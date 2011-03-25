package org.sankozi.rogueland.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.log4j.Logger;
import org.sankozi.rogueland.model.Actor;
import org.sankozi.rogueland.model.Tile;

/**
 *
 * @author sankozi
 */
public class FontPainter implements TilePainter{
    private final static Logger LOG = Logger.getLogger(FontPainter.class);
    
    private Font font;
    private FontMetrics metrics;

    private final ConcurrentMap<String,PainterOptions> optionsCache = new ConcurrentHashMap<String, PainterOptions>();

    {
        try {
            font = new Font("Monospaced", Font.BOLD, 30);
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }
    private int dy;
    private int dx;

    @Override
    public Rectangle getPixelLocation(Rectangle rect, Point location) {
        int x = location.x - rect.x;
        int y = location.y - rect.y;
        return new Rectangle(x * dx, y * dy, dx, dy);
    }

    private static class PainterOptions{
        String character;
        Color color;
    }

    private void drawActor(Graphics g, Actor actor, int x, int y){
        PainterOptions po = optionsCache.get(actor.getName());
        if(po == null){
            po = new PainterOptions();
            if(actor.getName().contains("player")){
                po.character = "@";
                po.color = Color.WHITE;
            } else {
                po.character = "@";
                po.color = Color.RED;
            }
            optionsCache.put(actor.getName(), po);
        }
        g.setColor(po.color);
        g.drawString(po.character, x, y);
    }

    private void initMetrics(Graphics g){
        metrics = g.getFontMetrics(font);
        dy = metrics.getHeight() - 3;
        dx = metrics.charWidth('#') - 3;
    }

    @Override
    public void paint(Rectangle rect, Tile[][] tiles, Graphics g) {
        LOG.trace("font painter!");
        initMetrics(g);

        g.setFont(font);
        g.setColor(Color.BLACK);
        g.fillRect(rect.x * dx, rect.y * dy, rect.width * dx,  rect.height * dy);

        int y = rect.y * dy + metrics.getAscent();
        for(int iy = rect.y; iy < rect.height; ++iy){
            int x = rect.x * dx;
            for(int ix = rect.x; ix < rect.width; ++ix){
                Actor actor = tiles[ix][iy].actor;
                if(actor != null){
                    drawActor(g, actor,x,y);
                } else {
                    drawField(g,tiles[ix][iy], x, y);
                }
                x += dx;
            }
            y += dy;
        }
    }

    private void drawField(Graphics g, Tile tile, int x, int y) {
        switch (tile.type) {
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
    }
}
