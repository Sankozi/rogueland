package org.sankozi.rogueland.model;

import org.apache.logging.log4j.*;
import org.sankozi.rogueland.generator.ItemGenerator;

/**
 * PlayerClass
 *
 * @author sankozi
 */
public final class PlayerClass {
    private final static Logger LOG = LogManager.getLogger(PlayerClass.class);

    public static final PlayerClass NULL = new PlayerClass(ItemGenerator.NULL);

    private final ItemGenerator itemGenerator;

    public PlayerClass(ItemGenerator itemGenerator) {
        this.itemGenerator = itemGenerator;
    }

    public ItemGenerator getItemGenerator(){
        return itemGenerator;
    }
}
