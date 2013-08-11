package org.sankozi.rogueland.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sankozi.rogueland.model.Level;
import org.sankozi.rogueland.model.Tile;

import java.util.Random;

/**
 * TestLevelGenerator
 *
 * @author sankozi
 */
public class TestLevelGenerator {
    private final static Logger LOG = LogManager.getLogger(TestLevelGenerator.class);

    public static void generate(Level level){
        int width = level.getDim().width;
        int height = level.getDim().height;
        Tile[][] tiles = level.getTiles();
        for(int x = 0; x < width; ++x){
            for(int y = 0; y < height; ++y){
                tiles[x][y].type = Tile.Type.SAND;
            }
        }
    }
}