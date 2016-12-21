package com.thegongoliers.input;

public interface TwoAxisAccelerometer {
	Accelerometer getX();

	Accelerometer getY();

	public static TwoAxisAccelerometer create(Accelerometer x, Accelerometer y) {
		return new TwoAxisAccelerometer() {

			@Override
			public Accelerometer getY() {
				return y;
			}

			@Override
			public Accelerometer getX() {
				return x;
			}
		};
	}
}
