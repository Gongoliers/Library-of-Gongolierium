package com.thegongoliers.output.interfaces;

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
	void backward(double speed);

	/**
	 * Rotate left at the given speed
	 * 
	 * @param speed
	 *            The speed of rotation from 0 to 1 inclusive.
	 */
	void rotateLeft(double speed);

	/**
	 * Rotate right at the given speed
	 * 
	 * @param speed
	 *            The speed of rotation from 0 to 1 inclusive.
	 */
	void rotateRight(double speed);

	/**
	 * Operate the robot with an arcade control.
	 * 
	 * @param speed
	 *            The speed to drive at from -1 to 1 inclusive. Positive speed
	 *            is forward.
	 * @param rotation
	 *            The rotation to drive at from -1 to 1 inclusive. Positive
	 *            rotation is right/clockwise.
	 */
	void arcade(double speed, double rotation);

	/**
	 * Operate the robot with a tank control. Positive speeds are forward.
	 * 
	 * @param left
	 *            The speed to drive the left train at.
	 * @param right
	 *            The speed to drive the right train at.
	 */
	void tank(double left, double right);
}
