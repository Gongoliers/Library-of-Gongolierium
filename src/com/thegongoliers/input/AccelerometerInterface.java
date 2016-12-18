package com.thegongoliers.input;

import com.thegongoliers.math.LinearAlgebra.Vector;

import edu.wpi.first.wpilibj.interfaces.Accelerometer;

/**
 * An interface for using accelerometers with added on features.
 * 
 */
public interface AccelerometerInterface extends Accelerometer {

	/**
	 * Gets the tilt of the robot (angle to ground).
	 * 
	 * @return The tilt of the robot in degrees.
	 */
	public double getTilt();

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
