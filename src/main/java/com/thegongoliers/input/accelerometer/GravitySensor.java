package com.thegongoliers.input.accelerometer;

import com.kylecorry.geometry.Vector3;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public class GravitySensor implements Accelerometer {

	private Accelerometer accel;

	private Vector3 gravity;

	private static final double alpha = 0.8;

	/**
	 * Attempts to remove the linear acceleration from the accelerometer.
	 * 
	 * @param accel
	 *            The accelerometer to filter out linear acceleration.
	 */
	public GravitySensor(Accelerometer accel) {
		this.accel = accel;
		gravity = new Vector3(accel.getX(), accel.getY(), accel.getZ());
	}

	@Override
	public void setRange(Range range) {
		accel.setRange(range);
	}

	@Override
	public double getX() {
		gravity.x = lowPassFilter(gravity.x, accel.getX());
		return gravity.x;
	}

	@Override
	public double getY() {
		gravity.y = lowPassFilter(gravity.y, accel.getY());
		return gravity.y;
	}

	@Override
	public double getZ() {
		gravity.z = lowPassFilter(gravity.z, accel.getZ());
		return gravity.z;
	}

	private double lowPassFilter(double prevVal, double newVal) {
		return alpha * prevVal + (1 - alpha) * newVal;
	}

}
