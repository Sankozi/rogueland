package org.sankozi.rogueland.control;

import org.sankozi.rogueland.model.Coords;
import org.sankozi.rogueland.model.Dim;

/**
 * Interface for finding things on Level
 * @author Michał
 */
public interface Locator {

	/**
	 * Returns player location
	 * @return 
	 */
	Coords getPlayerLocation();

    Dim getLevelDim();
	
}
