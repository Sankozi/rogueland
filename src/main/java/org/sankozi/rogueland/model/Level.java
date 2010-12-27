package org.sankozi.rogueland.model;

/**
 *
 * @author sankozi
 */
public class Level {

    public final static int WIDTH = 20;
    public final static int HEIGHT = 20;

    Tile[][] tiles;

    Controls controls;

    {
        tiles = new Tile[WIDTH][];
        for(int x =0; x<WIDTH; ++x){
            tiles[x] = new Tile[HEIGHT];
            for(int y = 0; y < HEIGHT; ++ y){
                tiles[x][y] = new Tile();
                tiles[x][y].type = Tile.Type.values()[(y + x) % 3];
            }
        }
    }

    public Tile[][] getTiles(){
        return tiles;
    }

    //TODO usunąć i zrobić poprawnie
    public void setControls(Controls controls){
        this.controls = controls;
    }

}
