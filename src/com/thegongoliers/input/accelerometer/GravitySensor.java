package com.thegongoliers.input.accelerometer;

import com.thegongoliers.geometry.Vector3;

import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public class GravitySensor implements Accelerometer {

	private Accelerometer accel;

	/**
	 * Attempts to remove the linear acceleration from the accelerometer.
	 * 
	 * @param accel
	 *            The accelerometer to filter out linear acceleration.
	 */
	public GravitySensor(Accelerometer accel) {
		this.accel = accel;
	}

	@Override
	public void setRange(Range range) {
		accel.setRange(range);
	}

	@Override
	public double getX() {
		Vector3 original = new Vector3(accel.getX(), accel.getY(), accel.getZ());
		return original.normalize().x;
	}

	@Override
	public double getY() {
		Vector3 original = new Vector3(accel.getX(), accel.getY(), accel.getZ());
		return original.normalize().y;
	}

	@Override
	public double getZ() {
		Vector3 original = new Vector3(accel.getX(), accel.getY(), accel.getZ());
		return original.normalize().z;
	}

}
