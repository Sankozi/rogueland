package org.sankozi.rogueland.gui;

import java.awt.*;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.logging.log4j.*;
import org.sankozi.rogueland.control.Game;
import org.sankozi.rogueland.model.*;

import javax.annotation.Nullable;

/**
 * TilePainter that uses font symbols for representing various tiles
 * @author sankozi
 */
public class FontPainter implements TilePainter{
    private final static Logger LOG = LogManager.getLogger(FontPainter.class);
    
    private Font font;
    private FontMetrics metrics;

    private int tileHeight;
    private int tileWidth;

    private final ConcurrentMap<String,PainterOptions> optionsCache = new ConcurrentHashMap<>();
	private final Map<Direction, Character> directionChars = new EnumMap<>(Direction.class);

    {
		directionChars.put(Direction.NW, '\\');
		directionChars.put(Direction.N, '|');
		directionChars.put(Direction.NE, '/');
		
		directionChars.put(Direction.W, '-');
		directionChars.put(Direction.E, '-');

		directionChars.put(Direction.SW, '/');
		directionChars.put(Direction.S, '|');
		directionChars.put(Direction.SE, '\\');
        try {
            font = new Font("Monospaced", Font.BOLD, 30);
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    @Override
    public Rectangle getTileRectangle(Game game, int width, int height, Coords location) {
		DrawingContext dc = new DrawingContext(width, height, game);

        return new Rectangle(
				(location.x - dc.startTileX) * tileWidth + dc.startPixelX,
				(location.y - dc.startTileY) * tileHeight + dc.startPixelY - metrics.getAscent(),
				tileWidth, tileHeight);
    }

    @Nullable
    @Override
    public Tile getTilePixelLocation(Game game, int width, int height, Coords pixelCoords) {
        DrawingContext dc = new DrawingContext(width, height, game);
        int tileX = (pixelCoords.x - dc.startPixelX) / tileWidth + dc.startTileX;
        int tileY = (pixelCoords.y - dc.startPixelY + metrics.getAscent()) / tileHeight+ dc.startTileY;
//        LOG.info(dc);
//        LOG.info("getTilePixelLocation(" + pixelCoords + ")=" + tileX + "," + tileY);
        if(tileX < 0 || tileY < 0 || tileX >= game.getLevel().getWidth() || tileX >= game.getLevel().getHeight()){
            return null;
        } else {
            return game.getLevel().getTiles()[tileX][tileY];
        }
    }

    private static class PainterOptions{
        String character;
        Color color;
    }

    private void drawActor(Graphics g, Actor actor, int x, int y){
        PainterOptions po = optionsCache.get(actor.getObjectName());
        if(po == null){
            po = new PainterOptions();
            if(actor.getObjectName().contains("player")){
                po.character = "@";
                po.color = Color.WHITE;
            } else {
                po.character = "@";
                po.color = Color.RED;
            }
            optionsCache.put(actor.getObjectName(), po);
        }
        g.setColor(po.color);
        g.drawString(po.character, x, y);
    }

	private void drawSword(Game game, Graphics g, int startingPixelX, int startingX, int startingPixelY, int startingY) {
        Player p = game.getPlayer();
        if(p.isArmed()){
            g.setColor(Color.WHITE);
            Coords location = p.getLocation();
            Direction dir = p.getWeaponDirection();
            g.drawString(directionChars.get(dir).toString(),
                    startingPixelX + tileWidth * (location.x - startingX + dir.dx),
                    startingPixelY + tileHeight * (location.y - startingY + dir.dy));
        }
//		LOG.info("paint end");
	}

    private void initMetrics(Graphics g){
        metrics = g.getFontMetrics(font);
        tileHeight = metrics.getHeight() - 3;
        tileWidth = metrics.charWidth('#') - 3;
    }

    private final class DrawingContext {
        int widthInPixels;
        int heightInPixels;

        int widthInTiles;
        int heightInTiles;

        int startTileX;
        int startTileY;

        int startPixelX;
        int startPixelY;

        DrawingContext(int width, int height, Game game){
            widthInPixels = width - width % tileWidth;
            heightInPixels = height - height % tileHeight;
            startPixelX = 0;
            startPixelY = metrics.getAscent();

            //tile coordinates
            widthInTiles = width / tileWidth;
            heightInTiles = height / tileHeight;
            Coords playerLocation = game.getPlayer().getLocation();
            startTileX = playerLocation.x - widthInTiles  / 2;
            if(startTileX < 0){
                startPixelX -= startTileX * tileWidth;
                widthInTiles += startTileX;
                startTileX = 0;
            }
            startTileY = playerLocation.y - heightInTiles / 2;
            if(startTileY < 0){
                startPixelY -= startTileY * tileHeight;
                heightInTiles += startTileY;
                startTileY = 0;
            }
        }

        @Override
        public String toString() {
            return "DrawingContext{" +
                    "widthInPixels=" + widthInPixels +
                    ", heightInPixels=" + heightInPixels +
                    ", widthInTiles=" + widthInTiles +
                    ", heightInTiles=" + heightInTiles +
                    ", startTileX=" + startTileX +
                    ", startTileY=" + startTileY +
                    ", startPixelX=" + startPixelX +
                    ", startPixelY=" + startPixelY +
                    '}';
        }
    }

    @Override
    public void paint(Game game, Graphics g, int width, int height) {
//        LOG.debug("filling with color " + g.getColor());
        g.fillRect(0,0, width, height);
		if(!game.isInitialized()){
			return;
		}
//		LOG.info("paint start : size ->" + width + " " + height);
		Tile[][] tiles = game.getLevel().getTiles();

		if(metrics == null){
        	initMetrics(g);
		}

        DrawingContext dc = new DrawingContext(width, height, game);

        g.setFont(font);
        g.setColor(Color.BLACK);
		//clean part with black
        g.fillRect(0, 0, width, height);

		//fill with symbols
        int y = dc.startPixelY;
        for(int iy = dc.startTileY; iy < dc.heightInTiles; ++iy){
			int x = dc.startPixelX;
			for(int ix = dc.startTileX; ix < dc.widthInTiles; ++ix){
				Actor actor = tiles[ix][iy].actor;
				if(actor != null){
					drawActor(g, actor,x,y);
				} else {
					drawField(g,tiles[ix][iy], x, y);
				}
				x += tileWidth;
			}
            y += tileHeight;
        }
		drawSword(game, g, dc.startPixelX, dc.startTileX, dc.startPixelY, dc.startTileY);
//		LOG.info("paint end");
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
