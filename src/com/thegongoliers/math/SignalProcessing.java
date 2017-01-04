package com.thegongoliers.math;

public class SignalProcessing {

	public static double nextIIR(double pastIir, double newVal) {
		return (newVal + pastIir) / 2.0;
	}

	public static double nextIIR(double[] pastIirs, double newVal) {
		if (pastIirs.length == 0)
			return newVal;
		return nextIIR(pastIirs[pastIirs.length - 1], newVal);
	}

	public static double[] IIR(double[] values) {
		if (values.length == 0) {
			return new double[0];
		}
		double[] iirVals = new double[values.length];
		iirVals[0] = values[0];
		for (int i = 1; i < values.length; i++) {
			iirVals[i] = (values[i] + iirVals[i - 1]) / 2.0;
		}
		return iirVals;
	}

	public static double[] FIR(double[] values) {
		if (values.length == 0) {
			return new double[0];
		}
		double[] firVals = new double[values.length];
		firVals[0] = values[0];
		for (int i = 1; i < values.length; i++) {
			double s = 0;
			for (int j = 0; j < i; j++) {
				s += values[j];
			}
			firVals[i] = s / i;
		}
		return firVals;
	}

	public static double smooth(double lastSmoothedValue, double newValue, double smoothing,
			double timeSinceLastUpdate) {
		return lastSmoothedValue + timeSinceLastUpdate * (newValue - lastSmoothedValue) / smoothing;
	}

	public static double smooth(double lastSmoothedValue, double newValue, double smoothing) {
		return smooth(lastSmoothedValue, newValue, smoothing, 1);
	}

	public static double[] smooth(double[] values, double smoothing, double updateTime) {
		double[] newValues = new double[values.length];
		double lastValue = 0;
		for (int i = 0; i < values.length; i++) {
			newValues[i] = smooth(lastValue, values[i], smoothing, updateTime);
			lastValue = values[i];
		}
		return newValues;
	}

	public static double[] smooth(double[] values, double smoothing) {
		return smooth(values, smoothing, 1);
	}

}
