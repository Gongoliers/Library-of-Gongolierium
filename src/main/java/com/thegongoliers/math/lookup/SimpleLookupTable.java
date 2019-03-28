package com.thegongoliers.math.lookup;

import com.thegongoliers.GongolieriumException;

/**
 * A simple lookup table which assumes the keys scale linearly with an offset
 */
public class SimpleLookupTable implements LookupTable {

    private double[] values;
    private double offset;

    /**
     * Constructor
     * @param values the values of the lookup table
     * @param offset the offset to subtract from the keys before using the lookup table. Defaults to 0
     */
    public SimpleLookupTable(double[] values, double offset){
        if (values == null || values.length == 0){
            throw new GongolieriumException("Lookup table can't be null or empty");
        }
        this.offset = offset;
        this.values = values;
    }

    /**
     * Constructor
     * @param values the values of the lookup table
     */
    public SimpleLookupTable(double[] values){
        this(values, 0);
    }

    /**
     * @see LookupTable#get(double)
     */
    @Override
    public double get(double key) {
        int modifiedKey = (int) Math.max(0, Math.min(values.length - 1, Math.round(key - offset)));
        return values[modifiedKey];
    }
}
