package org.sankozi.rogueland.gui.render;

import org.apache.logging.log4j.*;
import org.sankozi.rogueland.model.Param;
import org.sankozi.rogueland.model.WeaponAttack;
import org.sankozi.rogueland.model.coords.Direction;
import org.sankozi.rogueland.model.effect.Effect;
import org.sankozi.rogueland.model.effect.WeaponEffect;

import java.util.Map;

/**
 * Utils
 *
 * @author sankozi
 */
class Utils {
    private final static Logger LOG = LogManager.getLogger(Utils.class);

    static void renderEffects(Iterable<Effect> effects, StringBuilder sb) {
        for(Effect effect : effects) {
            renderEffect(effect, sb);
        }
    }

    static void renderEffect(Effect effect, StringBuilder sb){
        sb.append("<table><tr valign='top'><td><table>");
        if(effect instanceof WeaponEffect){
            WeaponEffect weaponEffect = (WeaponEffect) effect;
            sb.append("<tr><td colspan='2'/>Swing damage:</td></tr>");
            weaponEffect.setWeaponAttack(WeaponAttack.get(Direction.N, WeaponAttack.WeaponMove.SWING_CLOCKWISE));
            renderEffectRows(weaponEffect, sb);
            sb.append("<tr><td colspan='2'/>Thrust damage:</td></tr>");
            weaponEffect.setWeaponAttack(WeaponAttack.get(Direction.N, WeaponAttack.WeaponMove.THRUST));
            renderEffectRows(weaponEffect, sb);
        } else {
            renderEffectRows(effect, sb);
        }
        sb.append("</table>");
    }

    private static void renderEffectRows(Effect effect, StringBuilder sb){
        Map<? extends Param,Float> descriptionParameters = effect.getDescriptionParameters();
        for(Param param : descriptionParameters.keySet()){
            sb.append("<tr><td>").append(param.name()).append("</td><td>")
                    .append(descriptionParameters.get(param)).append("</td></tr>");
        }
    }
}
