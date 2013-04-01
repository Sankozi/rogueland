package org.sankozi.rogueland.control;

import org.sankozi.rogueland.model.Actor;
import org.sankozi.rogueland.model.Coords;

/** @Author sankozi */
public interface LevelControl {

    void spawnActor(Actor actor, Coords coords);

}
