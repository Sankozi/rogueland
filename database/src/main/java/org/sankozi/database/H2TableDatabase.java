package org.sankozi.database;

import java.sql.Connection;

/**
 *
 * @author sankozi
 */
public final class H2TableDatabase {
	private final Connection con;
	private final KeyValueDescriptor desc;

	public H2TableDatabase(Connection con, KeyValueDescriptor desc){
		this.con = con;		
		this.desc = desc;
	}
	
}
