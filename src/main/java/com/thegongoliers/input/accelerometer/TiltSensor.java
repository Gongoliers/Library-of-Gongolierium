package com.thegongoliers.input.accelerometer;

import com.kylecorry.geometry.Vector3;
import com.thegongoliers.math.MathExt;

import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public class TiltSensor {
	private Accelerometer accel;
	private Vector3 calibration = new Vector3(0, 0, 0);

	public void calibrate() {
		Vector3 acceleration = getAcceleration();
		acceleration.x = calculateAngle(acceleration.x);
		acceleration.y = calculateAngle(acceleration.y);
		acceleration.z = calculateAngle(acceleration.z);
		calibration = acceleration;
	}


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
	public Vector3 getTilt() {
		Vector3 acceleration = getAcceleration();
		acceleration.x = calculateAngle(acceleration.x);
		acceleration.y = calculateAngle(acceleration.y);
		acceleration.z = calculateAngle(acceleration.z);
		return acceleration.add(calibration.multiply(-1));
	}

	private double calculateAngle(double accel){
		double y = MathExt.toRange(accel, -1, 1);
		return Math.toDegrees(Math.asin(y));
	}

	private Vector3 getAcceleration(){
		return new Vector3(accel.getX(), accel.getY(), accel.getZ());
	}
}
