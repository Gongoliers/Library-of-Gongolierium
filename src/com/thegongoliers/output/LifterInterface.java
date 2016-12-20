package com.thegongoliers.output;

public interface LifterInterface extends Stoppable {
	/**
	 * Lift up at the give speed
	 * 
	 * @param speed
	 *            The speed of the lifting mechanism from 0 to 1 inclusive
	 */
	void up(double speed);

	/**
	 * Lift down at the give speed
	 * 
	 * @param speed
	 *            The speed of the lifting mechanism from 0 to 1 inclusive
	 */
	void down(double speed);

	/**
	 * Determine if the lifting mechanism is at the bottom
	 * 
	 * @return True if the lifting mechanism is at the absolute bottom, false
	 *         otherwise
	 */
	boolean isAtBottom();

	/**
	 * Determine if the lifting mechanism is at the top
	 * 
	 * @return True if the lifting mechanism is at the absolute top, false
	 *         otherwise
	 */
	boolean isAtTop();

	/**
	 * Get the position of the lifting mechanism from 0 to 1 inclusive along its
	 * track where 0 is the bottom, and 1 is the top
	 * 
	 * @return The position of the lifting mechanism
	 */
	double getNormalizedPosition();
}
