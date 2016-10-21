package com.thegongoliers.input;

import com.thegongoliers.util.LinearAlgebra.Vector;

public interface AccelerometerInterface {
	public double getTilt();

	public double getY();

	public double getX();

	public double getZ();

	public void display();

	/**
	 * Gets an approximation of the linear acceleration (without gravity) that
	 * the robot is experiencing
	 * 
	 * @return The linear acceleration
	 */
	public Vector getLinearAcceleration();

	/**
	 * Gets an approximation of the gravitational acceleration (without linear)
	 * that the robot is experiencing
	 * 
	 * @return The gravitational acceleration
	 */
	public Vector getGravitationalAcceleration();

	/**
	 * Produces the output of the accelerometer as a vector
	 * 
	 * @return The acceleration vector
	 */
	public Vector asVector();
}
