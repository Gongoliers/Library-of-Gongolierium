package com.thegongoliers.output;

public interface DriveTrainInterface extends Stoppable {
	/**
	 * Drive forward at the given speed
	 * 
	 * @param speed
	 *            The speed of the robot from 0 to 1 inclusive.
	 */
	void forward(double speed);

	/**
	 * Drive in reverse at the given speed
	 * 
	 * @param speed
	 *            The speed of the robot from 0 to 1 inclusive.
	 */
	void reverse(double speed);

	/**
	 * Rotate counter clockwise at the given speed
	 * 
	 * @param speed
	 *            The speed of rotation from 0 to 1 inclusive.
	 */
	void rotateCounterClockwise(double speed);

	/**
	 * Rotate clockwise at the given speed
	 * 
	 * @param speed
	 *            The speed of rotation from 0 to 1 inclusive.
	 */
	void rotateClockwise(double speed);
}
