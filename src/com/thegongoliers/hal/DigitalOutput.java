package com.thegongoliers.hal;

public interface DigitalOutput {
	void set(boolean value);

	default public DigitalOutput combine(DigitalOutput other) {
		return (boolean value) -> {
			set(value);
			other.set(value);
		};
	}
}
