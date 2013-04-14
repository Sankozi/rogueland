package org.sankozi.rogueland.model;

import org.sankozi.rogueland.model.coords.Coords;
import org.sankozi.rogueland.model.coords.Dim;

/**
 *
 * @author sankozi
 */
public final class Level {
	private final Dim dim;
	
    Tile[][] tiles;

	public Level(int width, int height) {
        this.dim = new Dim(width, height);
        tiles = new Tile[width][];
        for(int x =0; x<width; ++x){
            tiles[x] = new Tile[height];
            for(int y = 0; y < height; ++ y){
                tiles[x][y] = new Tile();
            }
        }
    }

    public Tile getTile(Coords p){
        return tiles[p.x][p.y];
    }

    public Tile[][] getTiles(){
        return tiles;
    }

    public Dim getDim(){
        return dim;
    }
}
