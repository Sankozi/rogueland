package org.sankozi.rogueland.model;

import org.sankozi.rogueland.model.coords.Coords;
import org.sankozi.rogueland.model.coords.Dim;

import static com.google.common.base.Preconditions.checkState;

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

    /**
     * Changing location of actor
     * @param actor
     * @param toCoords
     */
    public void changeActorLocation(Actor actor, Coords toCoords){
        Tile from = getTile(actor.getLocation());
        Tile to = getTile(toCoords);
        checkState(from.actor == actor, "invalid actor/level state [actor=%s]", actor.getLocation());
        checkState(to.actor == null, "target location is not empty [target=%s]", toCoords);

        to.actor = from.actor;
        from.actor = null;
        actor.setLocation(toCoords);
    }

    public boolean validActionLocation(Coords actionCoords) {
        //location outside level
        if(!dim.containsCoordinates(actionCoords)) {
            return false;
        } else {
            //location inaccessible and that cannot be intercarted with
            Tile tile = tiles[actionCoords.x][actionCoords.y];
            return tile.type != Tile.Type.ROCK;
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
