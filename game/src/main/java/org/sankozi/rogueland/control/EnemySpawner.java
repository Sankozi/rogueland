package org.sankozi.rogueland.control;

import org.apache.logging.log4j.*;
import org.sankozi.rogueland.model.AiActor;
import org.sankozi.rogueland.model.coords.Direction;

/**
 * EnemySpawner
 *
 * @author sankozi
 */
public class EnemySpawner implements Observer {
    private final static Logger LOG = LogManager.getLogger(EnemySpawner.class);

    int nextTurnsToNextEnemy = 20;
    int turnsToNextEnemy;

    @Override
    public void attach(Locator level, LevelControl control) {
        turnsToNextEnemy = nextTurnsToNextEnemy;
        LOG.info("attaching");
    }

    @Override
    public void tick(Locator level, LevelControl control) {
        if(turnsToNextEnemy == 0) {
            turnsToNextEnemy = nextTurnsToNextEnemy;
            --nextTurnsToNextEnemy;
            control.spawnActor(new AiActor(), Direction.N);
            LOG.info("spawning enemy, next in {} turns", turnsToNextEnemy);
        } else {
            --turnsToNextEnemy;
        }
    }
}
