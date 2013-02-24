package org.sankozi.rogueland.model;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.apache.log4j.Logger;

/**
 * Object containing description for something.
 *
 * @author sankozi
 */
public final class Description {
    private final static Logger LOG = Logger.getLogger(Description.class);

    public final static Description EMPTY = stringDescription("");

    private final Supplier<String> description;

    private Description(Supplier<String> description){
        this.description = description;
    }

    public static Description stringDescription(String description){
        return new Description(Suppliers.ofInstance(description));
    }

    public static Description stringDescription(Supplier<String> description){
        return new Description(description);
    }

    public String getAsString(){
        return description.get();
    }
}
