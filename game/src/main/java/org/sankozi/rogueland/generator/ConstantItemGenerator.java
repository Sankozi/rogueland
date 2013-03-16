package org.sankozi.rogueland.generator;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import java.util.Arrays;
import java.util.Collections;
import org.apache.log4j.Logger;
import org.sankozi.rogueland.data.DataLoader;
import org.sankozi.rogueland.model.Item;
import org.sankozi.rogueland.model.ItemTemplate;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Item generator creating the same set of items every time.
 * @author sankozi
 */
public class ConstantItemGenerator implements ItemGenerator{
    private final static Logger LOG = Logger.getLogger(ConstantItemGenerator.class);
    
    private final Iterable<ItemTemplate> templates;

    public ConstantItemGenerator(Iterable<ItemTemplate> templates){
        for(ItemTemplate template : checkNotNull(templates, "templates cannot be null")){
            checkNotNull(template, "templates cannot contain null element");
        }
        this.templates = templates;
    }

    @Override
    public Iterable<Item> generate(float luck) {
        LOG.info("generating items");
        return Iterables.transform(templates, ig -> new Item(ig));
//                Arrays.asList(
//                simpleItem("simple-staff"),
//                simpleItem("quarterstaff"),
//                simpleItem("leather-armor"));
    }
}
