package org.sankozi.rogueland.control;

import org.sankozi.rogueland.model.Actor;
import org.sankozi.rogueland.model.coords.Coords;
import org.sankozi.rogueland.model.coords.Direction;

/** @Author sankozi */
public interface LevelControl {

    void spawnActor(Actor actor, Coords coords);

    /**
     * Spawns actor at certain side of playing field
     * @param actor actor to be put onto level
     * @param direction direction
     */
    void spawnActor(Actor actor, Direction direction);

}
