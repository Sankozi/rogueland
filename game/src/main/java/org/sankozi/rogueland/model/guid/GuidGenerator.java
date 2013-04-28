package org.sankozi.rogueland.model.guid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * GuidGenerator
 *
 * @author sankozi
 */
public final class GuidGenerator {
    private final static Logger LOG = LogManager.getLogger(GuidGenerator.class);

    private final static AtomicInteger guidGenerator = new AtomicInteger(0);

    public static int getNewGuid(){
        int ret = guidGenerator.incrementAndGet();
        LOG.info("getting new guid {}", ret);
        return ret;
    }

    private GuidGenerator(){}
}
