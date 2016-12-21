package com.thegongoliers.input;

import java.util.function.DoubleSupplier;

public interface Gyroscope extends Compass {
	double getRate();

	default public Gyroscope invert() {
		return create(() -> -getAngle(), () -> -getRate());
	}

	public static Gyroscope create(DoubleSupplier angle, DoubleSupplier rate) {
		return new Gyroscope() {

			private double zero = 0;

			@Override
			public Gyroscope zero() {
				zero = angle.getAsDouble();
				return this;
			}

			@Override
			public double getAngle() {
				return angle.getAsDouble() - zero;
			}

			@Override
			public double getRate() {
				return rate.getAsDouble();
			}
		};
	}
}
