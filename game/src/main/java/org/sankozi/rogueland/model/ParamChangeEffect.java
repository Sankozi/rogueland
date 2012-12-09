package org.sankozi.rogueland.model;

/**
 * 
 * @author sankozi
 */
public final class ParamChangeEffect extends Effect {
    private final String name;

    public ParamChangeEffect(String name) {
		super(Float.POSITIVE_INFINITY);
        this.name = name;
	}

	public ParamChangeEffect(String name, float finishTime) {
		super(finishTime);
        this.name = name;
	}
	
	@Override
	public void start(ParamAccessManager manager) {
		manager.accessDestroyableParam(Destroyable.Param.BLUNT_PROT).setChange(+5);
	}

	@Override
	public void end(ParamAccessManager manager) {
		manager.accessDestroyableParam(Destroyable.Param.BLUNT_PROT).setChange(0);
	}

	@Override
	public String getName() {
		return name;
	}
}
