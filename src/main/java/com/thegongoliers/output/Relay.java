package com.thegongoliers.output;

public interface Relay {

	/**
	 * Turn the relay on.
	 */
	void on();

	/**
	 * Turn the relay off.
	 */
	void off();

	/**
	 * Determines if the relay is on.
	 * 
	 * @return True if the relay is on.
	 */
	boolean isOn();

	/**
	 * Determines if the relay is off.
	 * 
	 * @return True if the relay is off.
	 */
	boolean isOff();
}
