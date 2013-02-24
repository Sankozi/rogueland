package org.sankozi.rogueland.model.effect;

import org.sankozi.rogueland.model.Description;

/**
 * Effect that immediately ends. Used to create effects that doesn't need to
 * end, for example inflicting damage
 * @author sankozi
 */
public abstract class InstantEffect extends Effect {
    public InstantEffect() {
        super(0f);
    }

    protected abstract Description apply(AccessManager manager);

    @Override
    public final Description start(AccessManager manager) {
        return apply(manager);
    }

    @Override
    public final void end(AccessManager manager) {
    }
}
