package org.sankozi.rogueland.model;

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
    private final int hashCode;

	Item(ItemTemplate template) {
		this.template = template;
		this.durability = template.destroyableParam(Param.MAX_DURABILITY);
        this.hashCode = template.hashCode();
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
	public String getName() {
		return "item";
	}

	ItemTemplate getTemplate() {
		return template;
	}

    public String getDescription(){
        return template.getName();
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        return true;
    }

    
}
