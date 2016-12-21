package com.thegongoliers.input;

import com.thegongoliers.geometry.Point;
import com.thegongoliers.math.MathExt;

public class JoystickTransformer {

	/**
	 * Changes the sensitivity of the joystick values. Call after applying other
	 * adjustments to the joystick values.
	 * 
	 * @param input
	 *            The values of the joystick.
	 * @param sensitivity
	 *            The sensitivity (greater than 0, where 0 is no movement)
	 * @return The adjusted controller input with the sensitivity applied.
	 */
	public static Point sensitivity(Point input, double sensitivity) {
		return new Point(sensitivity(input.x, sensitivity), sensitivity(input.y, sensitivity),
				sensitivity(input.z, sensitivity));
	}

	/**
	 * Changes the sensitivity of the joystick value. Call after applying other
	 * adjustments to the joystick value.
	 * 
	 * @param input
	 *            The value of the joystick.
	 * @param sensitivity
	 *            The sensitivity (greater than 0, where 0 is no movement)
	 * @return The adjusted controller input with the sensitivity applied.
	 */
	public static double sensitivity(double input, double sensitivity) {
		return MathExt.toRange(input * sensitivity, -1, 1);
	}

	/**
	 * Smoothes the value of the joystick by a power. Should be applied after a
	 * deadzone transformation.
	 * 
	 * @param input
	 *            The value of the joystick.
	 * @param pow
	 *            The smoothing power.
	 * @return The smoothed joystick value.
	 */
	public static Point power(Point input, double pow) {
		return new Point(power(input.x, pow), power(input.y, pow), power(input.z, pow));
	}

	/**
	 * Smoothes the value of the joystick by a power. Should be applied after a
	 * deadzone transformation.
	 * 
	 * @param input
	 *            The value of the joystick.
	 * @param pow
	 *            The smoothing power.
	 * @return The smoothed joystick value.
	 */
	public static double power(double input, double pow) {
		return MathExt.sign(input) * Math.pow(Math.abs(input), pow);
	}

	/**
	 * Blocks movement within a threshold on each axis..
	 * 
	 * @param input
	 *            The value of the joystick.
	 * @param thresh
	 *            The deadzone threshold.
	 * @return The adjusted joystick value with the deadzone.
	 */
	public static Point axialDeadzone(Point input, double thresh) {
		return new Point(axialDeadzone(input.x, thresh), axialDeadzone(input.y, thresh),
				axialDeadzone(input.z, thresh));
	}

	/**
	 * Blocks movement within a threshold on each axis..
	 * 
	 * @param input
	 *            The value of the joystick.
	 * @param thresh
	 *            The deadzone threshold.
	 * @return The adjusted joystick value with the deadzone.
	 */
	public static double axialDeadzone(double input, double thresh) {
		if (Math.abs(input) < thresh)
			return 0.0;
		return input;
	}

	/**
	 * Blocks movement with a radius of (0, 0)
	 * 
	 * @param input
	 *            The value of the joystick.
	 * @param thresh
	 *            The deadzone threshold.
	 * @return The adjusted joystick value with the deadzone.
	 */
	public static Point radialDeadzone(Point input, double thresh) {
		if (magnitude(input) < thresh)
			return Point.origin;
		return input;
	}

	/**
	 * Blocks movement with a radius of (0, 0) and scales the resulting good
	 * zones from 0 to +/- 1
	 * 
	 * @param input
	 *            The value of the joystick.
	 * @param thresh
	 *            The deadzone threshold.
	 * @return The adjusted joystick value with the deadzone.
	 */
	public static Point scaledRadialDeadzone(Point input, double thresh) {
		if (magnitude(input) < thresh)
			return Point.origin;
		return timesScalar(normalized(input), ((magnitude(input) - thresh) / (1 - thresh)));
	}

	private static Point timesScalar(Point p, double scalar) {
		return new Point(p.x * scalar, p.y * scalar, p.z * scalar);
	}

	private static Point normalized(Point p) {
		double mag = magnitude(p);
		return new Point(p.x / mag, p.y / mag, p.z / mag);
	}

	private static double magnitude(Point p) {
		return Math.sqrt(p.x * p.x + p.y * p.y + p.z * p.z);
	}
}
