package com.thegongoliers.util;
/**
 * A cache that associates keys with values.
 */
interface Cache<T, U> extends DataProvider<T, U> {
	/**
	 * Returns the number of cache misses since the object's instantiation.
	 * @return the number of cache misses since the object's instantiation.
	 */
	int getNumMisses ();
}
