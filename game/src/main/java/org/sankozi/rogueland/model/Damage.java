package org.sankozi.rogueland.model;

import org.sankozi.rogueland.model.Destroyable.Param;


/**
 *
 * @author sankozi
 */
public final class Damage {

    public enum Type implements org.sankozi.rogueland.model.Param{
        PIERCING(Param.PIERCING_PROT),
        SLASHING(Param.SLASHING_PROT),
        BLUNT(Param.BLUNT_PROT);

        private final Param resistanceParam;

        private Type(Param resistanceParam) {
            this.resistanceParam = resistanceParam;
        }

        public Param getResistanceParam(){
            return resistanceParam;
        }
    }

    public final Type type;
    public final int value;

    public Damage(Type type, int value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return value + " " + type + " damage";
    }

    public Description getDescription(){
        return Description.stringDescription(Integer.toString(value), " ", type.toString());
    }
}
