package org.sankozi.rogueland.resources;

import java.net.URL;

/**
 *
 * @author sankozi
 */
public class ResourceProvider {

    public static URL getLog4jProperties() {
        return ResourceProvider.class.getResource("log4j.properties");
    }
}
