package org.sankozi.rogueland.model;

/**
 * Direction in game
 * @author sankozi
 */
public enum Direction {
    NW, N, NE, W, C, E, SW, S, SE;

    /** Directions from number keypad i.e. 1 is SW, 6 is E */
    private static final Direction[] numpadDirections = {null, SW, S, SE, W, C, E, NW, N, NE};
    private static final Move[] moves =
        {Move.Go.NORTHWEST, Move.Go.NORTH, Move.Go.NORTHEAST,
         Move.Go.WEST,      Move.WAIT,     Move.Go.EAST,
         Move.Go.SOUTHWEST, Move.Go.SOUTH, Move.Go.SOUTHEAST};

	private static final Direction[][] diffDirections =
		{ new Direction[] {NW, W, SW},
		  new Direction[] {N,  C, S},
		  new Direction[] {NE, E, SE}};

	public static Direction fromDiff(int dx, int dy){
		return diffDirections[dx + 1] [dy + 1];
	}

    public static Direction fromNumpad(int dir){
        return numpadDirections[dir];
    }

    public Move toSingleMove(){
        return moves[this.ordinal()];
    }
}