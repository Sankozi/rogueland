package org.sankozi.rogueland.model.coords;

import org.sankozi.rogueland.model.Move;
import org.sankozi.rogueland.model.guid.Guid;
import org.sankozi.rogueland.model.guid.GuidGenerator;

/**
 * Direction in game
 * @author sankozi
 */
public enum Direction implements Guid{
    NW(-1,-1, 7), N( 0,-1, 8), NE(+1, -1, 9),
	 W(-1, 0, 4), C( 0, 0, 5),  E(+1,  0, 6),
	SW(-1,+1, 1), S( 0,+1, 2), SE(+1, +1, 3);

	public final int dx;
	public final int dy;

    public final int numpadNumber;

    private final int guid = GuidGenerator.getNewGuid();

	private Direction(int dx, int dy, int numpad) {
		this.dx = dx;
		this.dy = dy;
        this.numpadNumber = numpad;
	}

    /** Directions from number keypad i.e. 1 is SW, 6 is E */
    private static final Direction[] numpadDirections = {null, SW, S, SE, W, C, E, NW, N, NE};
	private static final Direction[] nextClockwise = {N, NE, E, NW, C, SE, W, SW, S};
	private static final Direction[] previousClockwise = {W, NW, N, SW, C, NE, S, SE, E};

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

    @Override
    public int getGuid() {
        return guid;
    }
}