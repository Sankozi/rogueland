package org.sankozi.rogueland.generator;

import com.google.inject.Inject;
import java.util.Arrays;
import java.util.Collections;
import org.apache.log4j.Logger;
import org.sankozi.rogueland.data.DataLoader;
import org.sankozi.rogueland.model.Item;

/**
 * Item generator creating the same set of items every time.
 * @author sankozi
 */
public class ConstantItemGenerator implements ItemGenerator{
    private final static Logger LOG = Logger.getLogger(ConstantItemGenerator.class);
    
    private final DataLoader dataLoader;

    @Inject
    public ConstantItemGenerator(DataLoader dataLoader){
        this.dataLoader = dataLoader;
    }

    @Override
    public Iterable<Item> generate(float luck) {
        LOG.info("generating items");
        return Arrays.asList(
                simpleItem("simple-staff"), 
                simpleItem("quarterstaff"),
                simpleItem("leather-armor"));
    }

    private Item simpleItem(String templateName){
        return new Item(dataLoader.getItemTemplate(templateName));
    }
}
