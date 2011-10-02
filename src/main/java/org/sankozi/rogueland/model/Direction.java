package org.sankozi.rogueland.model;

/**
 * Direction in game
 * @author sankozi
 */
public enum Direction {
    NW(-1,-1), N( 0,-1), NE(+1, -1), 
	 W(-1, 0), C( 0, 0),  E(+1,  0), 
	SW(-1,+1), S( 0,+1), SE(+1, +1);

	public final int dx;
	public final int dy;

	private Direction(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

    /** Directions from number keypad i.e. 1 is SW, 6 is E */
    private static final Direction[] numpadDirections = {null, SW, S, SE, W, C, E, NW, N, NE};
	private static final Direction[] nextClockwise = {N, NE, E, NW, C, SE, W, SW, S};
	private static final Direction[] previousClockwise = {W, NW, N, SW, C, NE, S, SE, E};

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

	public Direction nextClockwise(){
		return nextClockwise[this.ordinal()];
	}

	public Direction prevClockwise(){
		return previousClockwise[this.ordinal()];
	}

    public Move toSingleMove(){
        return moves[this.ordinal()];
    }
}