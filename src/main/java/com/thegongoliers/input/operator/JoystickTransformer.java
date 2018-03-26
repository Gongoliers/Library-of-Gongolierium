package com.thegongoliers.input.operator;

import com.thegongoliers.math.MathExt;

public class JoystickTransformer {

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
	public static double power(double input, double pow) {
		return MathExt.sign(input) * Math.pow(Math.abs(input), pow);
	}

	/**
	 * Blocks movement within a threshold on an axis.
	 * 
	 * @param input
	 *            The value of the joystick.
	 * @param thresh
	 *            The deadzone threshold.
	 * @return The adjusted joystick value with the deadzone.
	 */
	public static double deadzone(double input, double thresh) {
		if (Math.abs(input) < thresh)
			return 0.0;
		return input;
	}

	/**
	 * Blocks movement within a threshold on an axis, but scales the output to be in [-1, 1]
	 * @param input The value of the joystick.
	 * @param thresh The deadzone threshold.
	 * @return The adjusted joystick value with the deadzone.
	 */
	public static double scaledDeadzone(double input, double thresh){
		double scaleIntercept = thresh / (1.0 - thresh);
		double scaleSlope = 1.0 / (1.0 - thresh);

		double output = 0;
		if(input < -thresh){
			output = input * scaleSlope + scaleIntercept;
		} else if (input > thresh){
			output = input * scaleSlope - scaleIntercept;
		}

		return output;
	}


	/**
	 * Blocks movement with a radius of (0, 0)
	 * 
	 * @param x
	 *            The x value of the joystick.
	 * @param y
	 *            The y value of the joystick.
	 * @param thresh
	 *            The deadzone threshold.
	 * @return The adjusted joystick value with the deadzone where x is at
	 *         position 0 and y is at position 1.
	 */
	public static double[] radialDeadzone(double x, double y, double thresh) {
		if (Math.sqrt(x * x + y * y) < thresh)
			return new double[2];
		return new double[] { x, y };
	}

	/**
	 * Blocks movement with a radius of (0, 0) and scales the resulting good
	 * zones from 0 to +/- 1
	 * 
	 * @param x
	 *            The x value of the joystick.
	 * @param y
	 *            The y value of the joystick.
	 * @param thresh
	 *            The deadzone threshold.
	 * @return The adjusted joystick value with the scaled deadzone where x is
	 *         at position 0 and y is at position 1.
	 */
	public static double[] scaledRadialDeadzone(double x, double y, double thresh) {
		double mag = Math.sqrt(x * x + y * y);
		if (mag < thresh)
			return new double[2];
		double newX = (x / mag) * ((mag - thresh) / (1 - thresh));
		double newY = (y / mag) * ((mag - thresh) / (1 - thresh));
		return new double[] { newX, newY };
	}
}
