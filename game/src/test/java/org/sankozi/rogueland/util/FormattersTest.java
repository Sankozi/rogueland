package org.sankozi.rogueland.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.*;
import org.sankozi.rogueland.model.coords.Coords;
import static org.junit.Assert.*;

import static org.sankozi.rogueland.util.Formatters.*;

/**
 * FormattersTest
 *
 * @author sankozi
 */
public class FormattersTest {
    private final static Logger LOG = LogManager.getLogger(FormattersTest.class);

    @Test
    public void formatParamTest(){
        assertEquals("1",formatParam(1f));
        assertEquals("2.1",formatParam(2.11f));
        assertEquals("3.1",formatParam(3.199f));
        assertEquals("40",formatParam(40.0009f));
    }
}
