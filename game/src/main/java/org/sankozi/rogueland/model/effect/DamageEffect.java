package org.sankozi.rogueland.model.effect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.sankozi.rogueland.model.Damageable;
import org.sankozi.rogueland.model.Damage;
import org.sankozi.rogueland.model.Description;
import org.sankozi.rogueland.model.Param;

/**
 *
 * @author sankozi
 */
public class DamageEffect extends InstantEffect {
    private final Iterable<Damage> damages;

    private Map<Damage.Type, Float> descriptionParameters;

    private DamageEffect(Iterable<Damage> damages){
        this.damages = damages;
    }
    
    public static DamageEffect simpleDamageEffect(Damage damage){
        DamageEffect ret = new DamageEffect(Collections.singleton(damage));
        return ret;
    }

    public static DamageEffect multiDamageEffect(Map<Damage.Type, Integer> values){
        ArrayList<Damage> damages = Lists.newArrayListWithCapacity(values.size());
        for(Map.Entry<Damage.Type, Integer> entry : values.entrySet()){
            damages.add(new Damage(entry.getKey(), entry.getValue()));
        }
        return new DamageEffect(damages);
    }
    
    @Override
    protected Description apply(AccessManager manager) {
        Damageable damageable = manager.getDamagable();
        for(Damage dam : damages){
            int res = damageable.protection(dam.type);
            if(res < dam.value){
                damageable.damage(dam.value - res);
            }
        }
        return Description.stringDescription(Suppliers.memoize( ()-> {
            StringBuilder sb = new StringBuilder();
            for(Damage dam : damages){
                int res = damageable.protection(dam.type);
                if(res == 0) {
                    sb.append(dam.value).append(' ').append(dam.type.toString()).append(" damage\n");
                } else if(res < dam.value) {
                    sb.append(dam.value - res)
                            .append(" (").append(dam.value).append(" - ").append(res).append(") ")
                            .append(dam.type.toString()).append(" damage\n");
                }
            }
            return sb.toString();
        }));
    }

    @Override
    public Map<? extends Param, Float> getDescriptionParameters() {
        if(descriptionParameters == null){
            ImmutableMap.Builder<Damage.Type, Float> builder = ImmutableMap.builder();
            for(Damage damage: damages){
                builder.put(damage.type, (float)damage.value);
            }
            descriptionParameters = builder.build();
        }
        return descriptionParameters;
    }

    @Override
    public String getObjectName() {
        return "effect.damage";
    }
}
