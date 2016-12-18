package com.thegongoliers.output;

public interface MecanumDriveTrainInterface extends DriveTrainInterface {
	/**
	 * Drive the robot left at the given speed
	 * 
	 * @param speed
	 *            The speed of the robot from 0 to 1 inclusive.
	 */
	void left(double speed);

	/**
	 * Drive the robot right at the given speed
	 * 
	 * @param speed
	 *            The speed of the robot from 0 to 1 inclusive.
	 */
	void right(double speed);
}