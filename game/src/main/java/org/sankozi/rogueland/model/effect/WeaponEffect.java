package org.sankozi.rogueland.model.effect;

import com.google.common.base.Function;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sankozi.rogueland.model.*;
import org.sankozi.rogueland.model.coords.Direction;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Mutable instant effect which inflict different damage based on WeaponAttack
 *
 * @author sankozi
 */
public class WeaponEffect extends InstantEffect {
    private final static Logger LOG = LogManager.getLogger(WeaponEffect.class);

    private final Function<WeaponAttack, Damage> damages;

    private WeaponAttack weaponAttack = WeaponAttack.get(Direction.N, WeaponAttack.WeaponMove.THRUST);

    private WeaponEffect(Function<WeaponAttack, Damage> damages){
        this.damages = damages;
    }

    public static WeaponEffect create(Function<WeaponAttack, Damage> damages){
        return new WeaponEffect(damages);
    }

    @Override
    protected Description apply(AccessManager manager) {
        Damage damage = damages.apply(weaponAttack);
        Damageable damageable = manager.getDamagable();
        int resistance = damageable.protection(damage.type);
        if(resistance < damage.value){
            damageable.damage(damage.value - resistance);
        }
        return Description.stringDescription(Suppliers.memoize(() -> {
            StringBuilder sb = new StringBuilder();
            if (resistance == 0) {
                sb.append(damage.value).append(' ').append(damage.type.toString()).append(" damage\n");
            } else if (resistance < damage.value) {
                sb.append(damage.value - resistance)
                        .append(" (").append(damage.value).append(" - ").append(resistance).append(") ")
                        .append(damage.type.toString()).append(" damage\n");
            }
            return sb.toString();
        }));
    }

    @Override
    public Map<? extends Param, Float> getDescriptionParameters() {
        Damage damage = damages.apply(weaponAttack);
        return ImmutableMap.<Param, Float>of(damage.type, (float) damage.value);
    }

    @Override
    public String getObjectName() {
        return "weaponEffect";
    }

    public WeaponAttack getWeaponAttack() {
        return weaponAttack;
    }

    public void setWeaponAttack(WeaponAttack weaponAttack) {
        this.weaponAttack = checkNotNull(weaponAttack, "weapon attack cannot be null");
    }
}
