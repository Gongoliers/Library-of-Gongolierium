package com.thegongoliers.hal;

public interface AnalogInput {
	double get();

	default public AnalogInput invert() {
		return () -> -get();
	}

	default public AnalogInput add(double value) {
		return () -> get() + value;
	}

	default public AnalogInput add(AnalogInput input) {
		return () -> get() + input.get();
	}

	default public AnalogInput subtract(double value) {
		return () -> get() - value;
	}

	default public AnalogInput subtract(AnalogInput input) {
		return () -> get() - input.get();
	}

	default public AnalogInput multiply(double value) {
		return () -> get() * value;
	}

	default public AnalogInput multiply(AnalogInput input) {
		return () -> get() * input.get();
	}

	default public AnalogInput divide(double value) {
		return () -> get() / value;
	}

	default public AnalogInput divide(AnalogInput input) {
		return () -> get() / input.get();
	}
}
