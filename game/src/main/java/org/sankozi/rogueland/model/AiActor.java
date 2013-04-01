package org.sankozi.rogueland.model;

import java.util.Random;
import org.apache.log4j.Logger;

import static org.sankozi.rogueland.MathUtils.*;

import org.sankozi.rogueland.control.Locator;
import org.sankozi.rogueland.model.effect.DamageEffect;
import org.sankozi.rogueland.model.effect.Effect;
import org.sankozi.rogueland.model.effect.EffectManager;

/**
 *
 * @author sankozi
 */
public class AiActor extends AbstractActor{
	private final static Logger LOG = Logger.getLogger(AiActor.class);
    private final static Damage damage = new Damage(Damage.Type.BLUNT, 5);
    private final EffectManager manager = EffectManager.forActor(this);
    
    Coords location;
    Random rand = new Random();
    
    public AiActor() {
        super(10);
        setDestroyableParam(Destroyable.Param.DURABILITY_REGEN, 0.125f);
        setDestroyableParam(Destroyable.Param.MAX_DURABILITY, 20);
    }

    @Override
    public Description getDescription() {
        return Description.build()
                .header(getName())
                .statEntry("Health", (float)getDurability())
                .statEntry(Destroyable.Param.MAX_DURABILITY, destroyableParam(Destroyable.Param.MAX_DURABILITY))
                .line("Damage : ", damage.getDescription().getAsString())
                .toDescription();
    }

    @Override
    public Move act(Level level, Locator locator) {
        return findDirectionToward(level, locator.playerLocation());
    }

	private Move findDirectionToward(Level level, Coords destination){
		int dx = clamp(destination.x - this.getLocation().x, -1, 1) ;
		int dy = clamp(destination.y - this.getLocation().y, -1, 1) ;
		int x = this.getLocation().x + dx;
		int y = this.getLocation().y + dy;
		
		//if player is nearby or tile toward it is free
		if((destination.x == x && destination.y == y) 
			||	level.getTiles()[x][y].isPassable()){
			return Direction.fromDiff(dx, dy).toSingleMove();
		} else {
			if(dx != 0 && dy != 0){ //diagonal direction -> try move closer
				if(level.getTiles()[x][y-dy].isPassable()){
					return Direction.fromDiff(dx, 0).toSingleMove();
				} else if (level.getTiles()[x-dx][y].isPassable()){
					return Direction.fromDiff(0, dy).toSingleMove();
				} else {
					return Move.WAIT;
				}
			} else if(dx != 0) {//dy == 0 -> lets try move diagonal
				if(level.getTiles()[x][y-1].isPassable()){
					return Direction.fromDiff(dx, -1).toSingleMove();
				} else if(level.getTiles()[x][y+1].isPassable()){
					return Direction.fromDiff(dx, +1).toSingleMove();
				} else {
					return Move.WAIT;
				}
			} else if(dy != 0) {//dx == 0 -> lets try move diagonal
				if(level.getTiles()[x-1][y].isPassable()){
					return Direction.fromDiff(-1, dy).toSingleMove();
				} else if(level.getTiles()[x+1][y].isPassable()){
					return Direction.fromDiff(+1, dy).toSingleMove();
				} else {
					return Move.WAIT;
				}
			} else {
				LOG.warn("0,0 AI move -> waiting");
				return Move.WAIT;
			}
		}
	}

	@Override
	public final boolean isArmed() {
		return false;
	}

	@Override
	public Coords getWeaponLocation() {
		return null;
	}

    @Override
    public Coords getLocation() {
        return location;
    }

    @Override
    public void setLocation(Coords point) {
        this.location = point;
    }

    @Override
    public String getObjectName() {
        return "actor.ai";
    }

    @Override
    public String getName() {
        return "Enemy";
    }

    @Override
    public Effect getBumpEffect() {
        return DamageEffect.simpleDamageEffect(Damage.Type.BLUNT, 5);
    }

    @Override
    public Effect getWeaponEffect() {
        return Effect.NULL;
    }

    @Override
    public EffectManager getEffectManager() {
        return manager;
    }
}
