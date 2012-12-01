package org.sankozi.rogueland.data;

import java.util.Collection;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;
import org.sankozi.rogueland.model.ItemTemplate;

/**
 *
 * @author sankozi
 */
public class DataLoaderTest {
	
	public DataLoaderTest() {
	}

	@Test
	public void testGetScriptNames() {
		Collection<String> scriptNames = new DataLoader().getScriptNames();
		assert !scriptNames.isEmpty() : "no script names";
		assert scriptNames.contains("items.cl");
	}

	@Test
	public void testLoadResource(){
		String resource = new DataLoader().loadResource("items.cl");
		assert resource.length() > 0: "resource not empty";
	}

	@Test
	public void testItemsResource(){
		Object res = new DataLoader().evaluateClResource("items.cl");
		assert res != null : "items not null";
		assert res instanceof Map : "items loads map";
	}

    @Test
    public void loadItemTemplates(){
        Map<String, ItemTemplate> templates = new DataLoader().loadItemTemplates();
		assert templates != null : "itemTemplates not null";
        assert !templates.isEmpty() : "itemTemplates not empty";
        assert templates.containsKey("test-item") : "itemTemplates contains test-item";
    }
}
