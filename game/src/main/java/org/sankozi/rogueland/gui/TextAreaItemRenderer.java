package org.sankozi.rogueland.gui;

import org.sankozi.rogueland.model.Item;
import org.sankozi.rogueland.model.Param;
import org.sankozi.rogueland.model.Player;

import java.util.Map;

/**
 * @author sankozi
 */
public class TextAreaItemRenderer extends TextAreaRenderer<Item> {
    @Override
    protected String getHtml(Item item) {
        StringBuilder sb = new StringBuilder(64);
        sb.append("<html><font size='6'>").append(item.getName()).append("</font><br/>")
                .append(item.getDescription().replace("\n", "<br/>"))
                .append("<table><tr valign='top'><td><table>");
        Map<? extends Param,Float> descriptionParameters = item.getEffect().getDescriptionParameters();
        for(Param param : descriptionParameters.keySet()){
            sb.append("<tr><td>").append(param.name()).append("</td><td>")
              .append(descriptionParameters.get(param)).append("</td></tr>");
        }
        sb.append("</table></html>");
        return sb.toString();
    }
}
