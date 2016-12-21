package com.thegongoliers.input;

import java.util.function.DoubleSupplier;

public interface DistanceSensor extends Zeroable {
	double getDistanceInches();

	default public double getDistanceFeet() {
		return getDistanceInches() / 12.0;
	}

	default public double getDistanceMeters() {
		return getDistanceInches() * 0.0254;
	}

	default public double getDistanceCentimeters() {
		return getDistanceMeters() * 100;
	}

	public static DistanceSensor create(DoubleSupplier supplier) {
		return new DistanceSensor() {

			private double zero = 0;

			@Override
			public DistanceSensor zero() {
				zero = supplier.getAsDouble();
				return this;
			}

			@Override
			public double getDistanceInches() {
				return supplier.getAsDouble() - zero;
			}
		};
	}
}
