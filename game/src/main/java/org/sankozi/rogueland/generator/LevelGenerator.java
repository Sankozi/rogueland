package org.sankozi.rogueland.generator;

import java.util.Random;
import org.apache.log4j.Logger;
import org.sankozi.rogueland.model.Level;
import org.sankozi.rogueland.model.Tile;

/**
 *
 * @author sankozi
 */
public class LevelGenerator {
	private final static Logger LOG = Logger.getLogger(LevelGenerator.class);

	public static void generate(Level level){
		Random rand = new Random();
		int width = level.getWidth();
		int height = level.getHeight();
        Tile[][] tiles = level.getTiles();
		for(int x = 0; x < width; ++x){
			for(int y = 0; y < height; ++y){
				if(rand.nextInt(5) == 0){
					tiles[x][y].type = Tile.Type.WALL;
				}
			}
		}
	}
}
