package com.thegongoliers.hal;

public interface DigitalInput {
	boolean get();

	default public DigitalInput invert() {
		return () -> !get();
	}

	public static DigitalInput and(DigitalInput b1, DigitalInput b2) {
		return () -> b1.get() && b2.get();
	}

	public static DigitalInput or(DigitalInput b1, DigitalInput b2) {
		return () -> b1.get() || b2.get();
	}
}
