package com.thegongoliers.output;

public class PID {

	private final double kp;
	private final double ki;
	private final double kd;
	private final double threshold;
	private double previousError;
	private double sumError = 0;
	private boolean first = true;
	boolean continuous = false;
	public double minInput = 0, maxInput = 1;

	/**
	 * Creates a simple PID calculator.
	 * 
	 * @param kp
	 *            The proportional constant.
	 * @param ki
	 *            The integral constant.
	 * @param kd
	 *            The differential constant.
	 * @param threshold
	 *            The threshold at which the sensor is considered on target.
	 * @param continuous
	 *            True if the device is continuous (for example if rotating 360
	 *            degrees from 0 you end up at 0 it is continuous)
	 */
	public PID(double kp, double ki, double kd, double threshold, boolean continuous) {
		this.kp = kp;
		this.ki = ki;
		this.kd = kd;
		this.threshold = threshold;
		this.continuous = continuous;
	}

	/**
	 * Creates a simple PID calculator.
	 * 
	 * @param kp
	 *            The proportional constant.
	 * @param ki
	 *            The integral constant.
	 * @param kd
	 *            The differential constant.
	 * @param threshold
	 *            The threshold at which the sensor is considered on target.
	 */
	public PID(double kp, double ki, double kd, double threshold) {
		this.kp = kp;
		this.ki = ki;
		this.kd = kd;
		this.threshold = threshold;
	}

	private double getContinuousError(double error) {
		if (continuous) {
			if (Math.abs(error) > (maxInput - minInput) / 2) {
				if (error > 0) {
					return error - (maxInput - minInput);
				} else {
					return error + (maxInput - minInput);
				}
			}
		}
		return error;
	}

	/**
	 * Calculates the output of the PID.
	 * 
	 * @param currentPosition
	 *            The current position.
	 * @param targetPosition
	 *            The target position.
	 * @return The output of the PID which calculates how to get to the target
	 *         position from the current position.
	 */
	public double getOutput(double currentPosition, double targetPosition) {
		double error = getContinuousError(targetPosition - currentPosition);
		if (first) {
			previousError = error;
			first = false;
		}
		sumError += error;
		double pidOutput = error * kp + sumError * ki + (error - previousError) * kd;
		previousError = error;
		return pidOutput;
	}

	/**
	 * Determines if the current position is close enough to the target position
	 * using the threshold.
	 * 
	 * @param currentPosition
	 *            The current position.
	 * @param targetPosition
	 *            The target position.
	 * @return True if it is at the target position (within the threshold).
	 */
	public boolean isAtTargetPosition(double currentPosition, double targetPosition) {
		return Math.abs(currentPosition - targetPosition) <= threshold;
	}
}
