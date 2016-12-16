package com.thegongoliers.input;

import com.thegongoliers.math.LinearAlgebra.Vector;

/**
 * An interface for using accelerometers with added on features.
 * 
 */
public interface AccelerometerInterface {

	/**
	 * Gets the tilt of the robot (angle to ground).
	 * 
	 * @return The tilt of the robot in degrees.
	 */
	public double getTilt();

	/**
	 * Gets the Y-Axis acceleration of the robot.
	 * 
	 * @return The Y-Axis acceleration in g-force.
	 */
	public double getY();

	/**
	 * Gets the X-Axis acceleration of the robot.
	 * 
	 * @return The X-Axis acceleration in g-force.
	 */
	public double getX();

	/**
	 * Gets the Z-Axis acceleration of the robot.
	 * 
	 * @return The Z-Axis acceleration of the robot in g-force.
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
	 * @return The linear acceleration in g-force
	 */
	public Vector getLinearAcceleration();

	/**
	 * Gets an approximation of the gravitational acceleration (without linear)
	 * that the robot is experiencing
	 * 
	 * @return The gravitational acceleration in g-force
	 */
	public Vector getGravitationalAcceleration();

	/**
	 * Produces the output of the accelerometer as a vector
	 * 
	 * @return The acceleration vector in g-force
	 */
	public Vector asVector();
}
