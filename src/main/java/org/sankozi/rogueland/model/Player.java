package org.sankozi.rogueland.model;

import com.google.common.collect.Sets;
import java.awt.Point;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 * Human Player
 * @author sankozi
 */
public class Player extends AbstractActor {
    private final static Logger LOG = Logger.getLogger(Player.class);

    private final static Damage damage = new Damage(Damage.Type.SLASHING, 10);

	private final static Set<Param> STATS = Sets.newEnumSet(Arrays.asList(
				Param.AGILITY, 
				Param.CHARISMA,
				Param.DEXTERITY,
				Param.ENDURANCE,
				Param.INTELLIGENCE,
				Param.LUCK,
				Param.STRENGTH,
				Param.WILLPOWER), 
			Param.class);

    private final EnumMap<Param, Float> params = new EnumMap<Param, Float>(Param.class);

    private final Controls controls;
    private Point location;

    public Player(Controls controls){
        super(10);
		for(Param param: STATS){
			setPlayerParam(param, 10f);
		}
        this.controls = controls;
        setDestroyableParam(Destroyable.Param.HEALTH_REGEN, 256);
        setDestroyableParam(Destroyable.Param.MAX_HEALTH, 20);
    }

    @Override
    public Move act(Level input) {
        try {
            return controls.waitForMove();
        } catch (InterruptedException ex) {
            LOG.error(ex.getMessage(), ex);
            return null;
        }
    }

	public enum Param {
		/* === STATS === */
		STRENGTH,
		DEXTERITY,
		AGILITY,
		ENDURANCE,
		INTELLIGENCE,
		WILLPOWER,
		CHARISMA,
		LUCK
	}

	public final void setPlayerParam(Param param, float value){
		params.put(param, value);
	}

	public final float playerParam(Param param){
		return params.get(param);
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
        return "actor/player";
    }

    @Override
    public Damage getPower() {
        return damage;
    }
}
