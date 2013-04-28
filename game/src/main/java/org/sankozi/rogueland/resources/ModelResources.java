package org.sankozi.rogueland.resources;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.inject.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sankozi.rogueland.model.Tile;

import java.awt.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.sankozi.rogueland.resources.ResourceProvider.*;

/**
 * Class providing resources for
 *
 * @author sankozi
 */
@Singleton
public final class ModelResources {
    private final static Logger LOG = LogManager.getLogger(ModelResources.class);

    Cache<Integer, Image> cachedImages = CacheBuilder.newBuilder()
                        .concurrencyLevel(1)
                        .expireAfterAccess(10, TimeUnit.MINUTES)
                        .build();

    public Image getImageForTileType(Tile.Type tileType){
        try {
            return cachedImages.get(tileType.getGuid(), () -> {
                return getImage("tiles/" + tileType.name().toLowerCase() + ".png");
            });
        } catch (ExecutionException e) {
            throw new RuntimeException("Error while loading image for tile type " + tileType.name()
                    + " " + e.getMessage(), e);
        }
    }
}
