package org.sankozi.rogueland.control;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sankozi.rogueland.generator.LevelGenerator;
import org.sankozi.rogueland.model.Level;

import javax.inject.Provider;

/**
 * LevelProvider
 *
 * @author sankozi
 */
public class LevelProvider implements Provider<Level> {
    private final static Logger LOG = LogManager.getLogger(LevelProvider.class);

    @Override
    public Level get() {
        Level ret = new Level(200,200);
        LevelGenerator.generate(ret);
        return ret;
    }
}
