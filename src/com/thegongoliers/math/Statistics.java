package com.thegongoliers.math;

import java.util.Arrays;

public class Statistics {
	public static double mean(double[] data) {
		return MathExt.sum(data) / data.length;
	}

	public static double median(double[] data) {
		double[] sorted = Arrays.copyOf(data, data.length);
		Arrays.sort(sorted);
		if (MathExt.isEven(data.length)) {
			return (sorted[data.length / 2] + sorted[data.length / 2 + 1]) / 2.0;
		} else {
			return sorted[data.length / 2];
		}
	}

	public static double variance(double[] data) {
		double sum = 0;
		double mean = mean(data);
		for (double val : data) {
			sum += MathExt.square(val - mean);
		}
		double n = data.length - 1;
		if (n <= 0)
			n = 1;
		return sum / n;
	}

	public static double standardDeviation(double[] data) {
		return Math.sqrt(variance(data));
	}

	public static double meanAbsoluteDeviation(double[] data) {
		double sum = 0;
		double mean = mean(data);
		for (double val : data) {
			sum += Math.abs(val - mean);
		}
		double n = data.length - 1;
		if (n <= 0)
			n = 1;
		return sum / n;
	}

	public static double movingAverage(double[] prevData, double currentValue, int size) {
		size = Math.min(prevData.length, size - 1);
		double sum = 0;
		sum += currentValue;
		for (int i = prevData.length - 1; i >= prevData.length - size; i--) {
			sum += prevData[i];
		}
		return sum / (size + 1.0);
	}

	public static double[] movingAverage(double[] values, int size) {
		double[] ma = new double[values.length];
		for (int i = 0; i < values.length; i++) {
			ma[i] = movingAverage(Arrays.copyOf(values, i), values[i], size);
		}
		return ma;
	}
}
