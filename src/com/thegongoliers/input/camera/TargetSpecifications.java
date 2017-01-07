package com.thegongoliers.input.camera;

import org.opencv.core.Range;

public interface TargetSpecifications {

	/**
	 * Gets the hue of the target from 0 to 180 degrees inclusive.
	 * 
	 * @return The target's hue.
	 */
	Range getHue();

	/**
	 * Gets the saturation of the target from 0 to 255 inclusive.
	 * 
	 * @return The target's saturation.
	 */
	Range getSaturation();

	/**
	 * Gets the value of the target from 0 to 255 inclusive.
	 * 
	 * @return The target's value.
	 */
	Range getValue();

	/**
	 * Gets the width of the target.
	 * 
	 * @return The target's width.
	 */
	double getWidth();

	/**
	 * Gets the height of the target.
	 * 
	 * @return The target's height.
	 */
	double getHeight();

	/**
	 * Gets the area of the target (not the bounding box, unless the target fills the entire bounding box).
	 * 
	 * @return The target's area.
	 */
	double getArea();
}
