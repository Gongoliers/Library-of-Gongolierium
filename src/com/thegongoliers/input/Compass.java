package com.thegongoliers.input;

import java.util.function.DoubleSupplier;

public interface Compass extends AngleSensor {
	default public double getHeading() {
		return getAngle() % 360;
	}

	default public Compass invert() {
		return create(() -> -getAngle());
	}

	public static Compass create(DoubleSupplier supplier) {
		return new Compass() {
			private double zero = 0;

			@Override
			public Compass zero() {
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
