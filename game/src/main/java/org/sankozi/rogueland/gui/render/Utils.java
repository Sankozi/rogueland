package org.sankozi.rogueland.gui.render;

import org.apache.logging.log4j.*;
import org.sankozi.rogueland.model.Param;
import org.sankozi.rogueland.model.effect.Effect;

import java.util.Map;

/**
 * Utils
 *
 * @author sankozi
 */
class Utils {
    private final static Logger LOG = LogManager.getLogger(Utils.class);

    static void renderEffect(Effect effect, StringBuilder sb){
        sb.append("<table><tr valign='top'><td><table>");
        Map<? extends Param,Float> descriptionParameters = effect.getDescriptionParameters();
        for(Param param : descriptionParameters.keySet()){
            sb.append("<tr><td>").append(param.name()).append("</td><td>")
                    .append(descriptionParameters.get(param)).append("</td></tr>");
        }
        sb.append("</table>");
    }
}
