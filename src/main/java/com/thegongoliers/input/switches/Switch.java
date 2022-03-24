package com.thegongoliers.input.switches;

import java.util.function.BooleanSupplier;

public interface Switch extends BooleanSupplier {
	/**
	 * Determines if the switch is triggered
	 * 
	 * @return true if the switch is triggered, false otherwise
	 */
	boolean isTriggered();

	@Override
	default boolean getAsBoolean(){
		return isTriggered();
	}

	/**
	 * Invert the output of the switch
	 * 
	 * @return The inverted switch
	 */
	static Switch invert(Switch s1) {
		return () -> !s1.isTriggered();
	}

	/**
	 * Combine two switches using the and operator
	 * 
	 * @param s1
	 *            The first switch
	 * @param s2
	 *            The second switch
	 * @return The combined switch consisting of s1 and s2 in an and gate.
	 */
	static Switch and(Switch s1, Switch s2) {
		return () -> s1.isTriggered() && s2.isTriggered();
	}

	/**
	 * Combine two switches using the or operator
	 * 
	 * @param s1
	 *            The first switch
	 * @param s2
	 *            The second switch
	 * @return The combined switch consisting of s1 and s2 in an or gate.
	 */
	static Switch or(Switch s1, Switch s2) {
		return () -> s1.isTriggered() || s2.isTriggered();
	}

	/**
	 * Combine two switches using the xor operator
	 *
	 * @param s1
	 *            The first switch
	 * @param s2
	 *            The second switch
	 * @return The combined switch consisting of s1 and s2 in an xor gate.
	 */
	static Switch xor(Switch s1, Switch s2) {
		return () -> s1.isTriggered() ^ s2.isTriggered();
	}
}
