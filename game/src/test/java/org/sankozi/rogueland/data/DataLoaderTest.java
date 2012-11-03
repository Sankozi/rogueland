package org.sankozi.rogueland.data;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sankozi
 */
public class DataLoaderTest {
	
	public DataLoaderTest() {
	}

	@Test
	public void testGetScriptNames() {
		assert !new DataLoader().getScriptNames().isEmpty() : "no script names";
	}
}
