package com.thegongoliers.input;

import com.thegongoliers.math.MathExt;
import com.thegongoliers.math.Position;

public class JoystickTransformer {

	/**
	 * Applies the function +/- b + (1 - b) * [a*x^3 + (1 - a)*x] to the joystick values. 
	 * @param input The 
	 * @param alpha
	 * @param beta
	 * @return
	 */
	public Position cubicTransform(Position input, double alpha, double beta) {
		return new Position(cubicTransform(input.getX(), alpha, beta), cubicTransform(input.getY(), alpha, beta));
	}

	public double cubicTransform(double input, double alpha, double beta) {
		double val = (1 - beta) * (alpha * Math.pow(input, 3) + (1 - alpha) * input);
		return (input >= 0 ? beta : -beta) + val;
	}

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
	public Position sensitivity(Position input, double sensitivity) {
		return new Position(sensitivity(input.getX(), sensitivity), sensitivity(input.getY(), sensitivity));
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
	public double sensitivity(double input, double sensitivity) {
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
	public Position power(Position input, double pow) {
		return new Position(power(input.getX(), pow), power(input.getY(), pow));
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
	public double power(double input, double pow) {
		return Math.copySign(1, input) * Math.pow(Math.abs(input), pow);
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
	public Position axialDeadzone(Position input, double thresh) {
		return new Position(axialDeadzone(input.getX(), thresh), axialDeadzone(input.getY(), thresh));
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
	public double axialDeadzone(double input, double thresh) {
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
	public Position radialDeadzone(Position input, double thresh) {
		if (magnitude(input) < thresh)
			return new Position(0, 0);
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
	public Position scaledRadialDeadzone(Position input, double thresh) {
		if (magnitude(input) < thresh)
			return new Position(0, 0);
		return timesScalar(normalized(input), ((magnitude(input) - thresh) / (1 - thresh)));
	}

	private Position timesScalar(Position p, double scalar) {
		return new Position(p.getX() * scalar, p.getY() * scalar);
	}

	private Position normalized(Position p) {
		double mag = magnitude(p);
		return new Position(p.getX() / mag, p.getY() / mag);
	}

	private double magnitude(Position p) {
		return Math.sqrt(p.getX() * p.getX() + p.getY() * p.getY());
	}
}
