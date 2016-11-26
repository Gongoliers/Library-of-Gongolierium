package com.thegongoliers.input;

import com.thegongoliers.util.MathExt;
import com.thegongoliers.util.Position;

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
	public Position sensitivity(Position input, double sensitivity) {
		Position p = timesScalar(input, sensitivity);
		Position adjP = new Position(0, 0);
		adjP.setX(MathExt.toRange(p.getX(), -1, 1));
		adjP.setY(MathExt.toRange(p.getY(), -1, 1));
		return adjP;
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
		return new Position(Math.copySign(1, input.getX()) * Math.pow(Math.abs(input.getX()), pow),
				Math.copySign(1, input.getY()) * Math.pow(Math.abs(input.getY()), pow));
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
		Position newPos = new Position(0, 0);
		if (Math.abs(input.getX()) >= thresh) {
			newPos.setX(input.getX());
		}
		if (Math.abs(input.getY()) >= thresh) {
			newPos.setY(input.getY());
		}
		return newPos;
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
