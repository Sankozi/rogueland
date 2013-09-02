package org.sankozi.rogueland.model;

import org.sankozi.rogueland.resources.ResourceProvider;

/**
 * Parameter of certain object, all Params are enums
 * @Author sankozi
 */
public interface Param {

    String name();

    default String getLabel()  {
        return ResourceProvider.getLabel(this.name());
    }
}
