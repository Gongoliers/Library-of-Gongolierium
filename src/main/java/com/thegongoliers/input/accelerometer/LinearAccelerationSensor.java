package com.thegongoliers.input.accelerometer;

import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public class LinearAccelerationSensor implements Accelerometer {

	private Accelerometer accel;

	private GravitySensor gravity;

	private static final double alpha = 0.8;

	/**
	 * Attempts to remove the gravitational acceleration from the accelerometer.
	 * 
	 * @param accel
	 *            The accelerometer to filter out gravitational acceleration.
	 */
	public LinearAccelerationSensor(Accelerometer accel) {
		this.accel = accel;
		gravity = new GravitySensor(accel);
	}

	@Override
	public void setRange(Range range) {
		accel.setRange(range);
	}

	@Override
	public double getX() {
		return accel.getX() - gravity.getX();
	}

	@Override
	public double getY() {
		return accel.getY() - gravity.getY();
	}

	@Override
	public double getZ() {
		return accel.getZ() - gravity.getZ();
	}
}
