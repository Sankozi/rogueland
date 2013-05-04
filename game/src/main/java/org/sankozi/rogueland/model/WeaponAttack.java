package org.sankozi.rogueland.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sankozi.rogueland.model.coords.Direction;

/**
 * Object representing type of weapon attack.
 *
 * WeaponAttack has direction (can be different than weapon direction from Actor!) and move.
 *
 * @author sankozi
 */
public final class WeaponAttack {
    private final static Logger LOG = LogManager.getLogger(WeaponAttack.class);

    public static enum WeaponMove {
        SWING_CLOCKWISE,
        SWING_COUNTERCLOCKWISE,
        THRUST
    }

    private final Direction direction;
    private final WeaponMove move;

    private WeaponAttack(Direction direction, WeaponMove move) {
        this.direction = direction;
        this.move = move;
    }

    /**
     * Returns WeaponAttack
     * @param direction direction of attack
     * @param move type of move
     * @return WeaponAttack, never null
     */
    public static WeaponAttack get(Direction direction, WeaponMove move){
        return new WeaponAttack(direction, move);
    }

    public Direction getDirection() {
        return direction;
    }

    public WeaponMove getMove() {
        return move;
    }
}
