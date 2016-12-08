package com.thegongoliers.util;

/**
 * A data provider that associates keys with values.
 */
interface DataProvider<T, U> {
	/**
	 * Returns the value associated with the specified key.
	 * @param key the key
	 * @return the value associated with the key
	 */
	U get (T key);
}
