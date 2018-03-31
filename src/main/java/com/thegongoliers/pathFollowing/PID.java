package com.thegongoliers.pathFollowing;

import com.thegongoliers.annotations.TestedBy;
import com.thegongoliers.math.MathExt;

@TestedBy(team = "5112", year = "2018")
public class PID {

	private final double kp;
	private final double ki;
	private final double kd;

	private double iState = 0;
	private double dState;

	private final double threshold;
	private boolean first = true;
	private boolean continuous = false;
	private double minInput = 0;
	private double maxInput = 1;
	private double maxOutput = 1;
	private double minOutput = -1;
	private double maxI = maxOutput;
	private double minI = minOutput;

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
			dState = currentPosition;
			first = false;
		}
		iState += error;
		iState = MathExt.toRange(iState, minI, maxI);

		double pTerm = kp * error;
		double iTerm = ki * iState;
		double dTerm = (dState - currentPosition) * kd;

        dState = currentPosition;

        double pidOutput = pTerm + iTerm + dTerm;

		return MathExt.toRange(pidOutput, minOutput, maxOutput);
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
