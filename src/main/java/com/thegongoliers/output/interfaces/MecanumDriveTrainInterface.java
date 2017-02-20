package com.thegongoliers.output.interfaces;

public interface MecanumDriveTrainInterface extends DriveTrainInterface {
	/**
	 * Drive the robot left at the given speed
	 * 
	 * @param speed
	 *            The speed of the robot from 0 to 1 inclusive.
	 */
	void strafeLeft(double speed);

	/**
	 * Drive the robot right at the given speed
	 * 
	 * @param speed
	 *            The speed of the robot from 0 to 1 inclusive.
	 */
	void strafeRight(double speed);

	/**
	 * Operate the mecanum drivetrain.
	 * 
	 * @param x
	 *            The speed in the x direction from -1 to 1 inclusive. Negative
	 *            x is left.
	 * @param y
	 *            The speed in the y direction from -1 to 1 inclusive. Negative
	 *            y is forward.
	 * @param rotation
	 *            The rotation speed from -1 to 1 inclusive. Negative rotation
	 *            is left.
	 */
	void mecanum(double x, double y, double rotation);
}
