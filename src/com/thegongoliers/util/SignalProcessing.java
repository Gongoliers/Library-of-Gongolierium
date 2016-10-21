package com.thegongoliers.util;

public class SignalProcessing {

	public static double nextIRR(double pastIrr, double newVal) {
		return (newVal + pastIrr) / 2.0;
	}

	public static double nextIRR(double[] pastIrrs, double newVal) {
		if (pastIrrs.length == 0)
			return newVal;
		return nextIRR(pastIrrs[pastIrrs.length - 1], newVal);
	}

	public static double[] IRR(double[] values) {
		if (values.length == 0) {
			return new double[0];
		}
		double[] irrVals = new double[values.length];
		irrVals[0] = values[0];
		for (int i = 1; i < values.length; i++) {
			irrVals[i] = values[i] / 2.0 + irrVals[i - 1] / 2.0;
		}
		return irrVals;
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

}
