package com.thegongoliers.output.interfaces;

public interface IntakeInterface extends Stoppable {
	/**
	 * Intake in at the given speed
	 * 
	 * @param speed
	 *            The speed of the intake from 0 to 1 inclusive
	 */
	void in(double speed);

	/**
	 * Intake in
	 */
	void in();

	/**
	 * Intake out at the given speed
	 * 
	 * @param speed
	 *            The speed of the intake from 0 to 1 inclusive
	 */
	void out(double speed);

	/**
	 * Intake out
	 */
	void out();
}
