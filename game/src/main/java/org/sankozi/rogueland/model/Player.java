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
    private final EffectManager manager ;
    private final EquippedItems equippedItems;
    
    private final EnumMap<Param, Float> params = new EnumMap<>(Param.class);
    
    private Controls controls;
    
    private Coords location;
	private Direction weaponDirection = Direction.N;
    private Effect weaponEffect = Effect.NULL;

    public Player(){
        this(PlayerClass.NULL);
    }

    public Player(PlayerClass playerClass){
        super(10);
		for(Param param: STATS){
			setPlayerParam(param, 10f);
		}
        setDestroyableParam(Destroyable.Param.DURABILITY_REGEN, 0.25f);
        setDestroyableParam(Destroyable.Param.MAX_DURABILITY, 20);
        this.equipment = new Inventory(playerClass.getItemGenerator().generate(0f));
        this.manager = EffectManager.forPlayer(this);
        this.equippedItems = new EquippedItems(this, equipment);
    }

    @Override
    public Move act(Level input, Locator location) {
        try {
            return controls.waitForMove();
        } catch (InterruptedException ex) {
			throw new IllegalStateException(ex);
        }
    }

    public enum Param implements org.sankozi.rogueland.model.Param{
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

    @Override
    public Effect getBumpEffect() {
        return DamageEffect.simpleDamageEffect(Damage.Type.BLUNT, 3);
    }

    @Override
    public Effect getWeaponEffect() {
        return weaponEffect;
    }

    @Override
    public EffectManager getEffectManager() {
        return manager;
    }

    @Override
    public Description getDescription() {
        return Description.stringDescription("You");
    }

    public void setWeaponEffect(Effect weaponEffect) {
        this.weaponEffect = weaponEffect;
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
        if(isArmed()){
		    return new Coords(location.x + weaponDirection.dx, location.y + weaponDirection.dy);
        } else {
            return null;
        }
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
        return "actor.player";
    }

    @Override
    public String getName() {
        return "Player";
    }

    @Override
	public boolean isArmed() {
		return weaponEffect != Effect.NULL;
	}

    public Inventory getEquipment(){
        return equipment;
    }

    public EquippedItems getEquippedItems(){
        return equippedItems;
    }

    public void setControls(Controls controls) {
        this.controls = controls;
    }
}
