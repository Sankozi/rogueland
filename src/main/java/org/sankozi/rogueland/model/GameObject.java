package org.sankozi.rogueland.model;

/**
 * Object in game world
 * @author sankozi
 */
public interface GameObject {

    /**
     * Returns name of the object, different objects can have same names
     *
     * Names are used to associate various resources with object - graphics, sounds etc.
     *
     * @return name, never null
     */
    String getName();

}
