package com.thegongoliers.output.interfaces;

public interface FlywheelInterface extends Stoppable {

	/**
	 * Spin up the flywheel to a given speed away from the robot
	 * 
	 * @param speed
	 *            The speed of the flywheel from 0 to 1 inclusive.
	 */
	void spinOutward(double speed);

	/**
	 * Spin up the flywheel to a given speed toward the robot
	 * 
	 * @param speed
	 *            The speed of the flywheel from 0 to 1 inclusive.
	 */
	void spinInward(double speed);

}
