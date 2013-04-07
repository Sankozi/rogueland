package org.sankozi.rogueland.resources;

import org.apache.logging.log4j.*;

/**
 *
 * @author sankozi
 */
public class ResourceException extends RuntimeException{

	private final static Logger LOG = LogManager.getLogger(ResourceException.class);
	private static final long serialVersionUID = 1L;

	public ResourceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceException(String message) {
		super(message);
	}
}
