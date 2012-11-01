package org.sankozi.database;

/**
 *
 * @author sankozi
 */
public interface KeyValueDatabase{
	
	KeyValueEntry get(String id);

	KeyValueEntry find(String id);
}
