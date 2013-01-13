package org.sankozi.rogueland.model.effect;

import org.sankozi.rogueland.model.Actor;
import org.sankozi.rogueland.model.Damagable;
import org.sankozi.rogueland.model.Destroyable;

/**
 * Object that manages access to various parameters
 * @author sankozi
 */
public interface AccessManager {
	ParamAccess accessDestroyableParam(Destroyable.Param param);
    ParamAccess accessActorParam(Actor.Param param);
    Damagable getDamagable();
}
