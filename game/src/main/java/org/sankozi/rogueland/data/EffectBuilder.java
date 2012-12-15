package org.sankozi.rogueland.data;

import com.google.common.base.CaseFormat;
import java.util.Map;
import java.util.Map.Entry;
import org.sankozi.rogueland.model.Destroyable;
import org.sankozi.rogueland.model.Effect;
import org.sankozi.rogueland.model.ParamChangeEffect;

/**
 *
 * @author sankozi
 */
enum EffectBuilder {
    PROTECTION{
        Effect create(Entry effectEntry) {
            Map protections = (Map) effectEntry.getValue();
            return new ParamChangeEffect("protection", 
                    LoaderUtils.toFloatMap(protections, Destroyable.Param.class)); 
        }
    };
    
    abstract Effect create(Entry effectEntry);

    static Effect build(Map.Entry effectEntry){
        return EffectBuilder.valueOf(
                CaseFormat.LOWER_HYPHEN.to(
                CaseFormat.UPPER_UNDERSCORE,
                effectEntry.getKey().toString())).create(effectEntry);
    }
}
