package org.sankozi.rogueland.model;

import java.awt.Point;
import java.util.Random;
import org.apache.log4j.Logger;

import static org.sankozi.rogueland.MathUtils.*;

/**
 *
 * @author sankozi
 */
public class AiActor extends AbstractActor{
	private final static Logger LOG = Logger.getLogger(AiActor.class);
    private final static Damage damage = new Damage(Damage.Type.BLUNT, 5);

    Point location;
    Random rand = new Random();

    public AiActor() {
        super(10);
        setDestroyableParam(Destroyable.Param.HEALTH_REGEN, 0.125f);
        setDestroyableParam(Destroyable.Param.MAX_HEALTH, 20);
    }

    @Override
    public Move act(Level level, Locator locator) {
        return findDirectionToward(level, locator.playerLocation());
    }

	private Move findDirectionToward(Level level, Point destination){
		int dx = clamp(destination.x - this.getLocation().x, -1, 1) ;
		int dy = clamp(destination.y - this.getLocation().y, -1, 1) ;
		int x = this.getLocation().x + dx;
		int y = this.getLocation().y + dy;
		if(level.getTiles()[x][y].type != Tile.Type.WALL){
			return Direction.fromDiff(dx, dy).toSingleMove();
		} else {
			LOG.info("point " + x + "," + y + " occupied");
			return Move.WAIT;
		}
	}

    @Override
    public Point getLocation() {
        return location;
    }

    @Override
    public void setLocation(Point point) {
        this.location = point;
    }

    @Override
    public String getName() {
        return "actor/ai";
    }

    @Override
    public Damage getPower() {
        return damage;
    }
}
