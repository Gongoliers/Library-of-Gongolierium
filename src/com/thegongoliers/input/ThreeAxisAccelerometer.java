package com.thegongoliers.input;

public interface ThreeAxisAccelerometer extends TwoAxisAccelerometer {
	Accelerometer getZ();

	public static ThreeAxisAccelerometer create(Accelerometer x, Accelerometer y, Accelerometer z) {
		return new ThreeAxisAccelerometer() {

			@Override
			public Accelerometer getY() {
				return y;
			}

			@Override
			public Accelerometer getX() {
				return x;
			}

			@Override
			public Accelerometer getZ() {
				return z;
			}
		};
	}
}
