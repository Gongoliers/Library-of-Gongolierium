package com.thegongoliers.math;

public class Pose {
	private double x, y, orientation;

	/**
	 * Creates a Pose which represents a 2D position and an angle representing a
	 * single rotation.
	 * 
	 * @param x
	 *            The x position
	 * @param y
	 *            The y position
	 * @param orientation
	 *            The orientation in degrees
	 */
	public Pose(double x, double y, double orientation) {
		this.x = x;
		this.y = y;
		this.orientation = orientation;
	}

	/**
	 * Get the x position
	 * 
	 * @return The x position
	 */
	public double getX() {
		return x;
	}

	/**
	 * Get the y position
	 * 
	 * @return The y position
	 */
	public double getY() {
		return y;
	}

	/**
	 * Get the orientation
	 * 
	 * @return The orientation in degrees
	 */
	public double getOrientation() {
		return orientation;
	}

}
