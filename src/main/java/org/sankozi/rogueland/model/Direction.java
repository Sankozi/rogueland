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

    public static Direction fromNumpad(int dir){
        return numpadDirections[dir];
    }

    public Move toSingleMove(){
        return moves[this.ordinal()];
    }
}