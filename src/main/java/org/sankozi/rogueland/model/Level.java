package org.sankozi.rogueland.model;

/**
 *
 * @author sankozi
 */
public final class Level {

    public final static int WIDTH = 20;
    public final static int HEIGHT = 20;

	private final int width = WIDTH;
	private final int height = HEIGHT;
	
    Tile[][] tiles;


    {
        tiles = new Tile[width][];
        for(int x =0; x<height; ++x){
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
