package org.sankozi.rogueland.model;

import com.google.common.base.Joiner;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.apache.logging.log4j.*;

import java.util.Map;

/**
 * Object containing description for something.
 *
 * @author sankozi
 */
public final class Description {
    private final static Logger LOG = LogManager.getLogger(Description.class);

    public final static Description EMPTY = stringDescription("");
    private final Supplier<String> description;

    public static final class DescriptionBuilder {
        StringBuilder sb = new StringBuilder();
        public DescriptionBuilder header(String header){
            sb.append(header).append('\n');
            return this;
        }

        public DescriptionBuilder line(String... line){
            for(String string:line){
                sb.append(string);
            }
            sb.append('\n');
            return this;
        }

        public DescriptionBuilder statEntry(Object entryKey, Float value) {
            if(value != null){
                sb.append(entryKey.toString()).append(" : ").append(value).append('\n');
            }
            return this;
        }

        public Description toDescription(){
            return new Description(()->sb.toString());
        }
    }

    private Description(Supplier<String> description){
        this.description = description;
    }

    public static DescriptionBuilder build(){
        return new DescriptionBuilder();
    }

    public static Description stringDescription(String description){
        return new Description(Suppliers.ofInstance(description));
    }

    public static Description stringDescription(String... description){
        return new Description(Suppliers.ofInstance(Joiner.on("").join(description)));
    }

    public static Description stringDescription(Supplier<String> description){
        return new Description(description);
    }

    public String getAsString(){
        return description.get();
    }
}
