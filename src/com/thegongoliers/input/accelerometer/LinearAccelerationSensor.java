package com.thegongoliers.input.accelerometer;

import com.thegongoliers.geometry.Vector3;

import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public class LinearAccelerationSensor implements Accelerometer {

	private Accelerometer accel;

	public LinearAccelerationSensor(Accelerometer accel) {
		this.accel = accel;
	}

	@Override
	public void setRange(Range range) {
		accel.setRange(range);
	}

	@Override
	public double getX() {
		Vector3 original = new Vector3(accel.getX(), accel.getY(), accel.getZ());
		Vector3 normalized = original.normalize();
		return original.subtract(normalized).x;
	}

	@Override
	public double getY() {
		Vector3 original = new Vector3(accel.getX(), accel.getY(), accel.getZ());
		Vector3 normalized = original.normalize();
		return original.subtract(normalized).y;
	}

	@Override
	public double getZ() {
		Vector3 original = new Vector3(accel.getX(), accel.getY(), accel.getZ());
		Vector3 normalized = original.normalize();
		return original.subtract(normalized).z;
	}

}
