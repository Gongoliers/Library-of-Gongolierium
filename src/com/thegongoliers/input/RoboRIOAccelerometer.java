package com.thegongoliers.input;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;

/**
 * Represents the built in accelerometer on the RoboRIO
 *
 */
public class RoboRIOAccelerometer extends AbstractAccelerometer {
	private BuiltInAccelerometer accelerometer = new BuiltInAccelerometer();

	public double getY() {
		return accelerometer.getY();
	}

	public double getX() {
		return accelerometer.getX();
	}

	public double getZ() {
		return accelerometer.getZ();
	}
}
