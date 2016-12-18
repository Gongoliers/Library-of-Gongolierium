package com.thegongoliers.geometry;

public class Pose2D {
	public double x, y, theta;

	/**
	 * Creates a Pose which represents a 2D position and an angle of orientation
	 * 
	 * @param x
	 *            The x position
	 * @param y
	 *            The y position
	 * @param theta
	 *            The orientation in radians
	 */
	public Pose2D(double x, double y, double theta) {
		this.x = x;
		this.y = y;
		this.theta = theta;
	}


	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Pose2D))
			return false;
		Pose2D o = (Pose2D) other;
		return o.x == x && o.y == y && o.theta == theta;
	}

}
