package com.thegongoliers.input;

import com.thegongoliers.math.LinearAlgebra.Vector;

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

	public Vector getLinearAcceleration() {
		Vector original = asVector();
		Vector gravity = getGravitationalAcceleration();
		return original.minus(gravity);
	}

	public Vector getGravitationalAcceleration() {
		Vector original = asVector();
		Vector direction = original.normalized();
		return direction.timesScalar(9.81);
	}

	public Vector asVector() {
		return new Vector(getX(), getY(), getZ());
	}

}
