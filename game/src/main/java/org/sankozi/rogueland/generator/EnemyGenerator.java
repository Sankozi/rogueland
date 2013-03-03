package org.sankozi.rogueland.generator;

import org.sankozi.rogueland.model.Actor;

/**
 * EnemyGenerator
 *
 * @author sankozi
 */
public interface EnemyGenerator {

    /**
     * Generates new enemies
     * @param power power of enemies to be created
     * @return new set of enemies
     */
    Iterable<Actor> generate(float power);
}
