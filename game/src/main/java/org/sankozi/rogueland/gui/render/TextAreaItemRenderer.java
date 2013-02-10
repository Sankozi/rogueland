package org.sankozi.rogueland.gui.render;

import org.sankozi.rogueland.model.Item;
import org.sankozi.rogueland.model.Param;
import org.sankozi.rogueland.model.effect.Effect;

import java.util.Map;

/**
 * @author sankozi
 */
public class TextAreaItemRenderer extends TextAreaRenderer<Item> {
    @Override
    protected String getHtml(Item item) {
        StringBuilder sb = new StringBuilder(64);
        sb.append("<html><font size='6'>").append(item.getName()).append("</font><br/>")
                .append(item.getDescription().replace("\n", "<br/>"));
        Utils.renderEffect(item.getEffect(), sb);
        sb.append("</html>");
        return sb.toString();
    }
}
