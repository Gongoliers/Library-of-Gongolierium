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

	public PID(double kp, double ki, double kd, double threshold, boolean continuous) {
		this.kp = kp;
		this.ki = ki;
		this.kd = kd;
		this.threshold = threshold;
		this.continuous = continuous;
	}

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

	public boolean isAtTargetPosition(double currentPosition, double targetPosition) {
		return Math.abs(currentPosition - targetPosition) <= threshold;
	}
}
