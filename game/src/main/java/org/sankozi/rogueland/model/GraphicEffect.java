package org.sankozi.rogueland.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sankozi.rogueland.model.coords.Coords;

import javax.annotation.Nullable;

/**
 * Object containing information of effect that can be rendered in some way but doesn't impact game in any way.
 *
 * @author sankozi
 */
public final class GraphicEffect {
    private final static Logger LOG = LogManager.getLogger(GraphicEffect.class);

    private final @Nullable Coords start;
    private final @Nullable Coords end;
    private final float power;
    private final Type type;

    public GraphicEffect(Coords start, Coords end, float power, Type type) {
        this.start = start;
        this.end = end;
        this.power = power;
        this.type = type;
    }

    public enum Type {
        PUSH
    }
}
