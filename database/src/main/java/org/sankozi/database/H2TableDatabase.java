package org.sankozi.database;

import java.sql.Connection;

/**
 *
 * @author sankozi
 */
public final class H2TableDatabase {
	private final Connection con;

	public H2TableDatabase(Connection con){
		this.con = con;		
	}
}
