package com.thegongoliers.input;

import java.util.function.DoubleSupplier;
import java.util.function.Function;

import com.thegongoliers.math.MathExt;

public interface JoystickInput {
	double read();

	default public JoystickInput invert() {
		return () -> -read();
	}

	default public JoystickInput scale(double scale) {
		return () -> read() * scale;
	}

	default public JoystickInput scale(DoubleSupplier scale) {
		return () -> read() * scale.getAsDouble();
	}

	default public JoystickInput map(Function<Double, Double> fn) {
		return () -> fn.apply(read());
	}
	
	default public JoystickInput power(double power){
		return () -> MathExt.sign(read()) * Math.abs(Math.pow(read(), power));
	}

	default public JoystickInput threshold(double thresh) {
		return () -> {
			if (Math.abs(read()) <= thresh) {
				return 0.0;
			} else {
				return read();
			}
		};
	}

}
