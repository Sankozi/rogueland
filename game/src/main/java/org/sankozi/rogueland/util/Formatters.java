package org.sankozi.rogueland.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Utility class used for formatting
 *
 * @author sankozi
 */
public final class Formatters {
    private final static Logger LOG = LogManager.getLogger(Formatters.class);

    /**
     * Format for float based values
     */
    private final static ThreadLocal<NumberFormat> PARAM_FORMAT = new ThreadLocal<NumberFormat>(){
        @Override
        protected NumberFormat initialValue() {
            DecimalFormat ret = new DecimalFormat("0.#", new DecimalFormatSymbols(Locale.US));
            ret.setRoundingMode(RoundingMode.DOWN);
            return ret;
        }
    };


    public static String formatParam(float value){
        return PARAM_FORMAT.get().format(value);
    }
}
