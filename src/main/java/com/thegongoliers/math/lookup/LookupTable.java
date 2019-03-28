package com.thegongoliers.math.lookup;

/**
 * A look up table
 */
public interface LookupTable {

    /**
     * Get the value of the key closest to the passed in key
     * @param key the key to lookup
     * @return the value at the key
     */
    double get(double key);
}
