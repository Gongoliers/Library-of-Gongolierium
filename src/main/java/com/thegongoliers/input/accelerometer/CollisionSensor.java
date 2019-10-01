package com.thegongoliers.input.accelerometer;

import com.thegongoliers.input.switches.Switch;
import com.thegongoliers.math.GMath;

import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public class CollisionSensor implements Switch {

	private final Accelerometer accel;
	private final double collisionMagnitudeThreshold;

	/**
	 * Determines if a collision occurred
	 * 
	 * @param accel
	 *            An accelerometer
	 * @param collisionMagnitudeThreshold
	 *            The magnitude of the acceleration in g-forces that determines
	 *            a collision
	 */
	public CollisionSensor(Accelerometer accel, double collisionMagnitudeThreshold) {
		this.accel = accel;
		this.collisionMagnitudeThreshold = collisionMagnitudeThreshold;
	}

	/**
	 * Triggers if the magnitude of the acceleration is greater or equal to the
	 * collision threshold
	 */
	@Override
	public boolean isTriggered() {
		return GMath.magnitude(accel.getX(), accel.getY(), accel.getZ()) >= collisionMagnitudeThreshold;
	}

}
