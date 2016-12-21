package com.thegongoliers.input;

import java.util.function.DoubleSupplier;

public interface AngleSensor extends Zeroable {
	double getAngle();

	default public AngleSensor invert() {
		return create(() -> -getAngle());
	}

	public static AngleSensor create(DoubleSupplier supplier) {
		return new AngleSensor() {

			private double zero = 0;

			@Override
			public AngleSensor zero() {
				zero = supplier.getAsDouble();
				return this;
			}

			@Override
			public double getAngle() {
				return supplier.getAsDouble() - zero;
			}
		};
	}
}
