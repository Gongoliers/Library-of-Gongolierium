package com.thegongoliers.input;

import com.thegongoliers.math.MathExt;

import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public class TiltSensor {
	private Accelerometer accel;

	public TiltSensor(final Accelerometer accel) {
		this.accel = accel;
	}

	public double getTilt() {
		double y = accel.getY();
		y = MathExt.toRange(y, -1, 1);
		return Math.toDegrees(Math.asin(y));
	}
}
