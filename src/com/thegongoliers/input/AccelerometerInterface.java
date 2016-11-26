package com.thegongoliers.input;

import com.thegongoliers.math.LinearAlgebra.Vector;

public interface AccelerometerInterface {

	/**
	 * Gets the tilt of the robot (angle to ground).
	 * 
	 * @return The tilt of the robot.
	 */
	public double getTilt();

	/**
	 * Gets the Y-Axis acceleration of the robot.
	 * 
	 * @return The Y-Axis acceleration.
	 */
	public double getY();

	/**
	 * Gets the X-Axis acceleration of the robot.
	 * 
	 * @return The X-Axis acceleration.
	 */
	public double getX();

	/**
	 * Gets the Z-Axis acceleration of the robot.
	 * 
	 * @return The Z-Axis acceleration of the robot.
	 */
	public double getZ();

	/**
	 * Display the values of the accelerometer to the SmartDashboard.
	 */
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
