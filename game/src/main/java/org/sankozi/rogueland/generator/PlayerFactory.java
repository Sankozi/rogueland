package org.sankozi.rogueland.generator;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.sankozi.rogueland.model.Player;

/**
 *
 * @author sankozi
 */
@Singleton
public class PlayerFactory implements Provider<Player>{

    private final ItemGenerator startingEquipmentGenerator;

    @Inject
    public PlayerFactory(ItemGenerator startingEquipmentGenerator) {
        this.startingEquipmentGenerator = startingEquipmentGenerator;
    }

    @Override
    public Player get() {
        return new Player(startingEquipmentGenerator.apply(0f));
    }
}
