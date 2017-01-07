package com.thegongoliers.input.camera;

import com.thegongoliers.geometry.Point;

interface TargetReportInterface {

	/**
	 * The confidence from 0 to 100 that the target located matched the given
	 * target specifications.
	 * 
	 * @return The confidence from 0 to 100 inclusive.
	 */
	int confidence();

	/**
	 * The angle to the target in degrees.
	 * 
	 * @return The angle in degrees.
	 */
	double angle();

	/**
	 * The distance to the target.
	 * 
	 * @return The distance.
	 */
	double distance();

	/**
	 * The location of the target in the image where each axis is from [-1, 1]
	 * and 0 is the center.
	 * 
	 * @return The location of the target in aiming coordinates.
	 */
	Point aimingCoordinates();

}
