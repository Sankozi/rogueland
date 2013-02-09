package org.sankozi.rogueland.gui;

import org.sankozi.rogueland.model.Item;

/**
 * @author sankozi
 */
public class TextAreaItemRenderer extends TextAreaRenderer<Item> {
    @Override
    protected String getHtml(Item item) {
        return item.getDescription().replace("\n", "<br/>");
    }
}
