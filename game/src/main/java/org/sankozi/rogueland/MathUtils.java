package org.sankozi.rogueland;

import org.apache.log4j.Logger;

/**
 *
 * @author sankozi
 */
public final class MathUtils {
	private final static Logger LOG = Logger.getLogger(MathUtils.class);

	private MathUtils(){}

	public static int clamp(int value, int first, int last){
		return value < first ? first 
				: value > last ? last : value;
	}
}
