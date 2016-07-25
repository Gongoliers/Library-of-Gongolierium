package com.thegongoliers.util;

public class PID {

	private final double kp;
	private final double ki;
	private final double kd;
	private final double threshold;
	private double previousError;
	private double sumError = 0;
	private boolean first = true;

	public PID(double kp, double ki, double kd, double threshold) {
		this.kp = kp;
		this.ki = ki;
		this.kd = kd;
		this.threshold = threshold;
	}

	public double getOutput(double currentPosition, double targetPosition) {
		double error = targetPosition - currentPosition;
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
