package com.thegongoliers.input;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;

/**
 * Represents the built in accelerometer on the RoboRIO
 *
 */
public class RoboRIOAccelerometer extends AbstractAccelerometer {
	private BuiltInAccelerometer accelerometer = new BuiltInAccelerometer();

	@Override
	public double getY() {
		return accelerometer.getY() * GRAVITY;
	}

	@Override
	public double getX() {
		return accelerometer.getX() * GRAVITY;
	}

	@Override
	public double getZ() {
		return accelerometer.getZ() * GRAVITY;
	}

	@Override
	public void setRange(Range range) {
		accelerometer.setRange(range);
	}
}
