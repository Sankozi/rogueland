package org.sankozi.rogueland;

import org.apache.logging.log4j.*;

/**
 *
 * @author sankozi
 */
public final class MathUtils {
	private final static Logger LOG = LogManager.getLogger(MathUtils.class);

	private MathUtils(){}

	public static int clamp(int value, int first, int last){
		return value < first ? first 
				: value > last ? last : value;
	}
}
