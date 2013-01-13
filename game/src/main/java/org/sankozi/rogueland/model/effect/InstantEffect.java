package org.sankozi.rogueland.model.effect;

/**
 * Effect that immediately ends. Used to create effects that doesn't need to
 * end, for example inflicting damage
 * @author sankozi
 */
public abstract class InstantEffect extends Effect{
    public InstantEffect() {
        super(0f);
    }

    protected abstract void apply(AccessManager manager);

    @Override
    public final void start(AccessManager manager) {
        apply(manager);
    }

    @Override
    public final void end(AccessManager manager) {
    }
}
