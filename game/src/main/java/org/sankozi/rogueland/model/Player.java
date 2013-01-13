package org.sankozi.rogueland.model;

import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Set;
import org.apache.log4j.Logger;
import org.sankozi.rogueland.model.effect.DamageEffect;
import org.sankozi.rogueland.model.effect.Effect;
import org.sankozi.rogueland.model.effect.EffectManager;

/**
 * Human Player
 * @author sankozi
 */
public class Player extends AbstractActor {
    private final static Logger LOG = Logger.getLogger(Player.class);
    private final EffectManager manager = EffectManager.forPlayer(this);

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

    private final Inventory equipment;
    
    private final EnumMap<Param, Float> params = new EnumMap<>(Param.class);
    
    private Controls controls;
    
    private Coords location;
	private Direction weaponDirection = Direction.N;

    public Player(){
        this(Collections.<Item>emptySet());
    }

    public Player(Iterable<Item> startingEquipment){
        super(10);
		for(Param param: STATS){
			setPlayerParam(param, 10f);
		}
        setDestroyableParam(Destroyable.Param.DURABILITY_REGEN, 0.25f);
        setDestroyableParam(Destroyable.Param.MAX_DURABILITY, 20);
        this.equipment = new Inventory(startingEquipment);
    }

    @Override
    public Move act(Level input, Locator location) {
        try {
            return controls.waitForMove();
        } catch (InterruptedException ex) {
			throw new IllegalStateException(ex);
        }
    }

    @Override
    public Effect getBumpEffect() {
        return DamageEffect.simpleDamageEffect(Damage.Type.BLUNT, 3);
    }

    @Override
    public Effect getWeaponEffect() {
        return DamageEffect.simpleDamageEffect(Damage.Type.SLASHING, 10);
    }

    @Override
    public EffectManager getEffectManager() {
        return manager;
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

	public Direction getWeaponDirection() {
		return weaponDirection;
	}

	@Override
	public Coords getWeaponLocation(){
		return new Coords(location.x + weaponDirection.dx, location.y + weaponDirection.dy);
	}

	public void setWeaponDirection(Direction weaponDirection) {
		this.weaponDirection = weaponDirection;
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
        return "actor/player";
    }

	@Override
	public boolean isArmed() {
		return true;
	}

    public Inventory getEquipment(){
        return equipment;
    }

    public void setControls(Controls controls) {
        this.controls = controls;
    }
}
