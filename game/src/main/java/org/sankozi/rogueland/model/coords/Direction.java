package org.sankozi.rogueland.model.coords;

import org.sankozi.rogueland.model.Move;
import org.sankozi.rogueland.model.guid.Guid;
import org.sankozi.rogueland.model.guid.GuidGenerator;

/**
 * Direction in game
 * @author sankozi
 */
public enum Direction implements Guid{
    NW(-1,-1, 7, 315), N( 0,-1, 8,   0), NE(+1, -1, 9,  45),
	 W(-1, 0, 4, 270), C( 0, 0, 5,   0),  E(+1,  0, 6,  90),
	SW(-1,+1, 1, 225), S( 0,+1, 2, 180), SE(+1, +1, 3, 135);

	public final int dx;
	public final int dy;

    public final int numpadNumber;

    /** angle looking from N clockwise */
    private final int angle;

    private final int guid = GuidGenerator.getNewGuid();

	private Direction(int dx, int dy, int numpad, int angle) {
		this.dx = dx;
		this.dy = dy;
        this.numpadNumber = numpad;

        this.angle = angle;
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

    public boolean isOnRightSideOf(Direction direction){
        if(this == C || direction == C) {
            throw new IllegalStateException("C direction cannot be compared with other direction");
        }
        if(this.angle < direction.angle){
            return this.angle + 360 - direction.angle < 180;
        } else {
            return this.angle - direction.angle < 180;
        }
    }

    @Override
    public int getGuid() {
        return guid;
    }
}