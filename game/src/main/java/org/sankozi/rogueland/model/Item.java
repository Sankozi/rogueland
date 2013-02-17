package org.sankozi.rogueland.model;

import org.sankozi.rogueland.model.effect.Effect;
import org.apache.log4j.Logger;
import org.sankozi.rogueland.model.Damage.Type;

/**
 * Destroyable game object that can be collected by Player, 
 * except durability Item object cannot be modified 
 * (in game every modification is really just replacement). 
 * Except methods isDestroyed and damage(int), calls are delegated to ItemTemplate instances
 * 
 * @author sankozi
 */
public final class Item implements Destroyable{
	private final static Logger LOG = Logger.getLogger(Item.class);

	private final ItemTemplate template;

	private float durability;

	public Item(ItemTemplate template) {
		this.template = template;
		this.durability = template.destroyableParam(Param.MAX_DURABILITY);
    }

	public Iterable<ItemType> getTypes(){
		return template.getTypes();
	}

	@Override
	public boolean isDestroyed() {
		return durability <= 0f;
	}
	
	@Override
	public void damage(int value) {
		durability -= value;
	}

	@Override
	public int getDurability() {
		return (int) durability;
	}

	@Override
	public void heal(float value) {
		durability = Math.min(template.destroyableParam(Param.MAX_DURABILITY), value + durability);
	}

	@Override
	public int protection(Type type) {
		return (int) template.destroyableParam(type.getResistanceParam());
	}

	@Override
	public float destroyableParam(Param param) {
		return template.destroyableParam(param);
	}

	@Override
	public void setDestroyableParam(Param param, float value) {
		LOG.warn("setDestroyableParam called on Item - call ignored", new Throwable());	
	}

	@Override
	public String getObjectName() {
		return "item";
	}

    public Effect getUsedEffect(){
        return template.getUsedEffect();
    }

    public Effect getWeaponEffect(){
        return template.getWeaponEffect();
    }

	ItemTemplate getTemplate() {
		return template;
	}

    public String getName(){
        return template.getName();
    }

    public String getDescription(){
        return template.getDescription();
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
