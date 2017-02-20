package com.thegongoliers.input.accelerometer;

import com.kylecorry.geometry.Vector3;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public class LinearAccelerationSensor implements Accelerometer {

	private Accelerometer accel;

	private Vector3 gravity;

	private final double alpha = 0.8;

	/**
	 * Attempts to remove the gravitational acceleration from the accelerometer.
	 * 
	 * @param accel
	 *            The accelerometer to filter out gravitational acceleration.
	 */
	public LinearAccelerationSensor(Accelerometer accel) {
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
		return accel.getX() - gravity.x;
	}

	@Override
	public double getY() {
		gravity.y = lowPassFilter(gravity.y, accel.getY());
		return accel.getY() - gravity.y;
	}

	@Override
	public double getZ() {
		gravity.z = lowPassFilter(gravity.z, accel.getZ());
		return accel.getZ() - gravity.z;
	}

	private double lowPassFilter(double prevVal, double newVal) {
		return alpha * prevVal + (1 - alpha) * newVal;
	}

}
