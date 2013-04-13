package org.sankozi.rogueland.control;

import org.apache.logging.log4j.*;

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
            LOG.info("spawning enemy, next in {} turns", turnsToNextEnemy);
        } else {
            --turnsToNextEnemy;
        }
    }
}
