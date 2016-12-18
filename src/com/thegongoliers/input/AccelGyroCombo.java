package com.thegongoliers.input;

import com.thegongoliers.geometry_msgs.Accel;

public class AccelGyroCombo {

	private GyroInterface gyro;
	private AccelerometerInterface accel;

	public AccelGyroCombo(AccelerometerInterface accelerometer, GyroInterface gyroscope) {
		accel = accelerometer;
		gyro = gyroscope;
	}

	public Accel get() {
		Accel acceleration = new Accel(accel.get(), gyro.get());
		return acceleration;
	}

}
