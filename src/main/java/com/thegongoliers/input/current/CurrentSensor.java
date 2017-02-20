package com.thegongoliers.input.current;

/**
 * This interface provides a layer of abstraction over current sensors, allowing
 * easy access to the current.
 */
public interface CurrentSensor {
	/**
	 * Get the current detected by the sensor in Amps.
	 * 
	 * @return The current in Amps.
	 */
	double getCurrent();
}
