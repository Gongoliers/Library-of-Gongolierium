package com.thegongoliers.math;

import java.util.Arrays;

public class Statistics {

	/**
	 * Calculates the statistical mean of the given data.
	 * 
	 * @param data
	 *            The data.
	 * @return The mean of the data.
	 */
	public static double mean(double[] data) {
		return MathExt.sum(data) / data.length;
	}

	/**
	 * Calculates the median of the given data.
	 * 
	 * @param data
	 *            The data.
	 * @return The median of the data.
	 */
	public static double median(double[] data) {
		double[] sorted = Arrays.copyOf(data, data.length);
		Arrays.sort(sorted);
		if (MathExt.isEven(data.length)) {
			return (sorted[data.length / 2] + sorted[data.length / 2 + 1]) / 2.0;
		} else {
			return sorted[data.length / 2];
		}
	}

	/**
	 * Calculates the variance of the given data.
	 * 
	 * @param data
	 *            The data.
	 * @return The variance of the data.
	 */
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

	/**
	 * Calculates the standard deviation of the given data.
	 * 
	 * @param data
	 *            The data.
	 * @return The standard deviation of the data.
	 */
	public static double standardDeviation(double[] data) {
		return Math.sqrt(variance(data));
	}

	/**
	 * Calculates the mean absolute deviation of the given data.
	 * 
	 * @param data
	 *            The data.
	 * @return The mean absolute deviation of the data.
	 */
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

	/**
	 * Calculates the moving average of the given data point.
	 * 
	 * @param prevData
	 *            The previous data points (not moving average).
	 * @param currentValue
	 *            The current data point.
	 * @param size
	 *            The size of the moving average window.
	 * @return The moving average of the data point.
	 */
	public static double movingAverage(double[] prevData, double currentValue, int size) {
		size = Math.min(prevData.length, size - 1);
		double sum = 0;
		sum += currentValue;
		for (int i = prevData.length - 1; i >= prevData.length - size; i--) {
			sum += prevData[i];
		}
		return sum / (size + 1.0);
	}

	/**
	 * Calculates the moving average over the given data.
	 * 
	 * @param values
	 *            The data.
	 * @param size
	 *            The size of the moving average window.
	 * @return The moving average of the data.
	 */
	public static double[] movingAverage(double[] values, int size) {
		double[] ma = new double[values.length];
		for (int i = 0; i < values.length; i++) {
			ma[i] = movingAverage(Arrays.copyOf(values, i), values[i], size);
		}
		return ma;
	}
}
