package org.sankozi.rogueland.gui.render;

import org.sankozi.rogueland.model.Item;

/**
 * @author sankozi
 */
public class TextAreaItemRenderer extends TextAreaRenderer<Item> {
    @Override
    protected String getHtml(Item item) {
        StringBuilder sb = new StringBuilder(64);
        sb.append("<html><font size='6'>").append(item.getName()).append("</font><br/>")
                .append(item.getDescription().replace("\n", "<br/>"));
        Utils.renderEffect(item.getUsedEffect(), sb);
        sb.append("</html>");
        return sb.toString();
    }
}
