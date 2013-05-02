package org.sankozi.rogueland.model;

import com.google.common.collect.ImmutableMap;
import org.sankozi.rogueland.model.coords.Direction;

import java.util.Map;

/**
 *
 * @author sankozi
 */
public interface Move {

    public static final Move WAIT = new Move(){};

    public static enum Go implements Move{
        NORTHWEST(Direction.NW),
        NORTH(Direction.N),
        NORTHEAST(Direction.NE),
        WEST(Direction.W),
        EAST(Direction.E),
        SOUTH(Direction.S),
        SOUTHEAST(Direction.SE),
        SOUTHWEST(Direction.SW);

        public final Direction direction;

        static final Map<Direction, Move> DIRECTION_TO_MOVE;

        static {
            ImmutableMap.Builder<Direction, Move> builder = ImmutableMap.<Direction, Move>builder();
            for(Go move : Go.values()){
                builder.put(move.direction, move);
            }
            builder.put(Direction.C, Move.WAIT);
            DIRECTION_TO_MOVE = builder.build();
        }

        public static Move fromDirection(Direction dir){
            Move ret = DIRECTION_TO_MOVE.get(dir);
            return ret;
        }

        private Go(Direction direction) {
            this.direction = direction;
        }
    }

	public static enum Rotate implements Move{
		CLOCKWISE,
		COUNTERCLOCKWISE
	}
}
