package com.thegongoliers.input;

import com.thegongoliers.geometry_msgs.Vector3;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class AbstractAccelerometer implements AccelerometerInterface {

	public double getTilt() {
		double y = getY();
		y = Math.min(1, y);
		y = Math.max(-1, y);
		return Math.toDegrees(Math.asin(y));
	}

	public void display() {
		SmartDashboard.putNumber("Accelerometer Y", getY());
		SmartDashboard.putNumber("Accelerometer X", getX());
		SmartDashboard.putNumber("Accelerometer X", getZ());
	}

	public Vector3 getLinearAcceleration() {
		Vector3 original = get();
		Vector3 gravity = getGravitationalAcceleration();
		return original.subtract(gravity);
	}

	public Vector3 getGravitationalAcceleration() {
		Vector3 original = get();
		Vector3 direction = original.normalize();
		return direction.multiply(GRAVITY);
	}

	public Vector3 get() {
		return new Vector3(getX(), getY(), getZ());
	}

}
