package com.thegongoliers.hal;

public interface AnalogOutput {
	void set(double value);

	default public AnalogOutput combine(AnalogOutput other) {
		return (double value) -> {
			set(value);
			other.set(value);
		};
	}
}
