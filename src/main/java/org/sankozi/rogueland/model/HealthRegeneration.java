package org.sankozi.rogueland.model;

/**
 *
 * @author sankozi
 */
public class HealthRegeneration implements Effect{

    private final int milisPerTurn;

    public HealthRegeneration(int milisPerTurn) {
        this.milisPerTurn = milisPerTurn;
    }

    @Override
    public void tick(Actor actor) {
        
    }

    @Override
    public String getName() {
        return "effect/health-regeneration";
    }

}
