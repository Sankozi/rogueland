package org.sankozi.rogueland.model;

/**
 * 
 * @author sankozi
 */
public final class ParamChangeEffect extends Effect {

	public ParamChangeEffect(float finishTime) {
		super(finishTime);
	}
	
	@Override
	public void start(ParamAccessManager manager) {
		manager.accessDestroyableParam(Destroyable.Param.BLUNT_PROT).setChange(+5);
	}

	@Override
	public void end(ParamAccessManager manager) {
		manager.accessDestroyableParam(Destroyable.Param.BLUNT_PROT).setChange(+5);
	}

	@Override
	public String getName() {
		return "";
	}
}
