package org.sankozi.rogueland.resources;

import org.apache.log4j.Logger;

/**
 *
 * @author sankozi
 */
public class ResourceException extends RuntimeException{

	private final static Logger LOG = Logger.getLogger(ResourceException.class);
	private static final long serialVersionUID = 1L;

	public ResourceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceException(String message) {
		super(message);
	}
}
