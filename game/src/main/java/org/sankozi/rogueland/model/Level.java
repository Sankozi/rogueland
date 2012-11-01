package org.sankozi.rogueland.model;

/**
 *
 * @author sankozi
 */
public final class Level {
	private final int width;
	private final int height;
	
    Tile[][] tiles;

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
        tiles = new Tile[width][];
        for(int x =0; x<width; ++x){
            tiles[x] = new Tile[height];
            for(int y = 0; y < height; ++ y){
                tiles[x][y] = new Tile();
            }
        }
    }

    public Tile[][] getTiles(){
        return tiles;
    }

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}
}
