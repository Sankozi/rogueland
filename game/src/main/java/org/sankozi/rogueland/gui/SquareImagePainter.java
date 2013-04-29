package org.sankozi.rogueland.gui;

import com.google.common.collect.ImmutableMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sankozi.rogueland.control.Game;
import org.sankozi.rogueland.model.Actor;
import org.sankozi.rogueland.model.Level;
import org.sankozi.rogueland.model.Player;
import org.sankozi.rogueland.model.Tile;
import org.sankozi.rogueland.model.coords.Coords;
import org.sankozi.rogueland.model.coords.Direction;
import org.sankozi.rogueland.resources.ModelResources;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

import static org.sankozi.rogueland.resources.ResourceProvider.*;
/**
 * SquareImagePainter
 *
 * @author sankozi
 */
public class SquareImagePainter implements TilePainter {
    private final static Logger LOG = LogManager.getLogger(SquareImagePainter.class);

    private final ModelResources modelResources;

    private final int tileHeight = 32;
    private final int tileWidth = 32;

    private Image slime = getImage("tiles/slime-1.png");
    private Image hero = getImage("tiles/hero-warrior.png");

    @Inject
    public SquareImagePainter(ModelResources modelResources) {
        this.modelResources = modelResources;
    }

    @Override
    public Rectangle getTileRectangle(Game game, int width, int height, Coords location) {
        DrawingContext dc = new DrawingContext(width, height, game);

        return new Rectangle(
                (location.x - dc.startTileX) * tileWidth + dc.startPixelX,
                (location.y - dc.startTileY) * tileHeight + dc.startPixelY,
                tileWidth, tileHeight);
    }

    @Nullable
    @Override
    public Tile getTilePixelLocation(Game game, int width, int height, Coords pixelCoords) {
        DrawingContext dc = new DrawingContext(width, height, game);
        int tileX = (pixelCoords.x - dc.startPixelX) / tileWidth + dc.startTileX;
        int tileY = (pixelCoords.y - dc.startPixelY) / tileHeight+ dc.startTileY;
//        LOG.info(dc);
//        LOG.info("getTilePixelLocation(" + pixelCoords + ")=" + tileX + "," + tileY);
        Level level = game.getLevel();
        if(!level.getDim().containsCoordinates(tileX, tileY)){
            return null;
        } else {
            return level.getTiles()[tileX][tileY];
        }
    }

    private static class PainterOptions{
        String character;
        Color color;
    }

    private void drawActor(Graphics g, Actor actor, int x, int y){
        if(actor.getObjectName().contains("player")){
            g.drawImage(hero, x, y, null);
        } else {
            g.drawImage(slime, x, y, null);
        }
    }

    private void drawSword(Game game, Graphics g, int startingPixelX, int startingX, int startingPixelY, int startingY) {
        Player p = game.getPlayer();
        if(p.isArmed()){
            g.setColor(Color.WHITE);
            Coords location = p.getLocation();
            Direction dir = p.getWeaponDirection();
            g.drawImage(modelResources.getImageForWeaponDirection(dir),
                    startingPixelX + tileWidth * (location.x - startingX) + tileWidth / 2 * dir.dx,
                    startingPixelY + tileHeight * (location.y - startingY) + tileHeight / 2 * dir.dy,
                    null);
        }
//		LOG.info("paint end");
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
            startPixelY = 0;

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

        DrawingContext dc = new DrawingContext(width, height, game);

        g.setColor(Color.BLACK);
        //clean part with black
        g.fillRect(0, 0, width, height);

        //fill with symbols
        int y = dc.startPixelY;
        int maxIY = dc.startTileY + dc.heightInTiles;
        for(int iy = dc.startTileY; iy < maxIY; ++iy){
            int x = dc.startPixelX;
            int maxIX = dc.startTileX + dc.widthInTiles;
            for(int ix = dc.startTileX; ix < maxIX; ++ix){
                Actor actor = tiles[ix][iy].actor;
                drawField(g,tiles[ix][iy], x, y);
                if(actor != null){
                    drawActor(g, actor,x,y);
                }
                x += tileWidth;
            }
            y += tileHeight;
        }
        drawSword(game, g, dc.startPixelX, dc.startTileX, dc.startPixelY, dc.startTileY);
//		LOG.info("paint end");
    }

    private void drawField(Graphics g, Tile tile, int x, int y) {
        g.drawImage(modelResources.getImageForTileType(tile.type), x, y, null);
    }
}
