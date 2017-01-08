package com.thegongoliers.input.accelerometer;

import com.thegongoliers.math.MathExt;

import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public class TiltSensor {
	private Accelerometer accel;

	/**
	 * Converts an accelerometer reading to tilt.
	 * 
	 * @param accel
	 *            The accelerometer to base tilt off of.
	 */
	public TiltSensor(Accelerometer accel) {
		this.accel = accel;
	}

	/**
	 * Calculates the tilt in degrees of the accelerometer.
	 * 
	 * @return The tilt in degrees.
	 */
	public double getTilt() {
		double y = accel.getY();
		y = MathExt.toRange(y, -1, 1);
		return Math.toDegrees(Math.asin(y));
	}
}
