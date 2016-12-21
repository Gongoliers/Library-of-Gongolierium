package com.thegongoliers.math;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.thegongoliers.geometry.Cylindrical;
import com.thegongoliers.geometry.Point;
import com.thegongoliers.geometry.Spherical;

public class MathExt {

	private MathExt() {
	}

	/**
	 * isOdd : int -> boolean
	 * 
	 * Determines if a number is odd
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isOdd(int value) {
		return divisibleBy(value, 2);
	}

	/**
	 * isEven : int -> boolean
	 * 
	 * Determines if a number is even
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEven(int value) {
		return !isOdd(value);
	}

	/**
	 * isPartOfNumber : char -> boolean
	 * 
	 * Determines if a symbol is a number, or part of a number such as - and .
	 * 
	 * @param symbol
	 * @return
	 */
	static boolean isPartOfNumber(char symbol) {
		return Character.isDigit(symbol) || symbol == '-' || symbol == '.';
	}

	public static Spherical toSpherical(Point p) {
		double rho = Math.sqrt(square(p.x) + square(p.y) + square(p.z));
		double theta = Math.atan2(p.y, p.x);
		double phi = Math.acos(p.z / rho);
		return new Spherical(rho, theta, phi);
	}

	public static Spherical toSpherical(Cylindrical c) {
		double rho = Math.sqrt(square(c.r) + square(c.z));
		double phi = Math.asin(c.r / rho);
		return new Spherical(rho, c.theta, phi);
	}

	public static Cylindrical toCylindrical(Point p) {
		double magnitude = Math.sqrt(Math.pow(p.x, 2) + Math.pow(p.y, 2));
		double angle = Math.atan2(p.y, p.x);
		return new Cylindrical(magnitude, angle, p.z);
	}

	public static Cylindrical toCylindrical(Spherical s) {
		return new Cylindrical(s.p * Math.sin(s.phi), s.theta, s.p * Math.cos(s.phi));
	}

	public static Point toCartesian(Cylindrical c) {
		return new Point(c.r * Math.cos(c.theta), c.r * Math.sin(c.theta), c.z);
	}

	public static Point toCartesian(Spherical s) {
		return new Point(s.p * Math.sin(s.phi) * Math.cos(s.theta), s.p * Math.sin(s.phi) * Math.sin(s.theta),
				s.p * Math.cos(s.phi));
	}

	/**
	 * sign : double -> int
	 * 
	 * Determines the sign of a value
	 * 
	 * Produces 1 if the value if positive, -1 if it is negative, and 0 if it is
	 * 0
	 * 
	 * @param value
	 *            The value which to get the sign from
	 * @return 1 if the value if positive, -1 if it is negative, and 0 if it is
	 *         0
	 */
	public static int sign(double value) {
		if (value > 0) {
			return 1;
		} else if (value < 0) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * percent : double double -> double
	 * 
	 * Calculates the percentage of the value
	 * 
	 * @param value
	 *            The initial value
	 * @param percent
	 *            The percent of the value to keep
	 * @return The percent of the initial value
	 */
	public static double percent(double value, double percent) {
		return value * percent / 100.0;
	}

	/**
	 * square : double -> double
	 * 
	 * Calculates the square of some value
	 * 
	 * @param value
	 *            The base value
	 * @return The square of value
	 */
	public static double square(double value) {
		return Math.pow(value, 2);
	}

	public static double normalize(double value, double min, double max) {
		return (value - min) / (max - min);
	}

	public static double[] normalize(double[] values) {
		double max = max(values);
		return toPrimitiveArray(toArrayList(values).stream().map(x -> x / max).collect(Collectors.toList()));
	}

	public static ArrayList<Double> toArrayList(double[] values) {
		ArrayList<Double> arrVals = new ArrayList<>();
		for (int i = 0; i < values.length; i++) {
			arrVals.add(values[i]);
		}
		return arrVals;
	}

	public static double[] toPrimitiveArray(List<Double> values) {
		double[] primValues = new double[values.size()];
		for (int i = 0; i < primValues.length; i++) {
			primValues[i] = values.get(i);
		}
		return primValues;
	}

	public static double average(double[] values) {
		return sum(values) / values.length;
	}

	public static double sum(double[] values) {
		double total = 0;
		for (double value : values) {
			total += value;
		}
		return total;
	}

	public static double min(double[] values) {
		if (values.length == 0) {
			return Double.NEGATIVE_INFINITY;
		}
		double min = values[0];

		for (double value : values) {
			if (min > value) {
				min = value;
			}
		}
		return min;
	}

	public static double max(double[] values) {
		if (values.length == 0) {
			return Double.POSITIVE_INFINITY;
		}
		double max = values[0];

		for (double value : values) {
			if (max < value) {
				max = value;
			}
		}
		return max;
	}

	public static double toRange(double value, double min, double max) {
		double newVal = Math.max(min, value);
		newVal = Math.min(newVal, max);
		return newVal;
	}

	public static int roundToInt(double value) {
		return (int) Math.round(value);
	}

	public static double roundPlaces(double value, int numPlaces) {
		double multiplier = Math.pow(10, numPlaces);
		return snap(value, 1 / multiplier);
	}

	public static double snap(double value, double nearest) {
		return Math.round(value / nearest) * nearest;
	}

	public static boolean divisibleBy(int value, int divisor) {
		return value % divisor == 0;
	}

	public static boolean spike(double[] values, double threshold) {
		double min = min(values);
		double max = max(values);
		return (max - min) >= threshold;
	}

	public static double meanSquaredError(double[] actual, double[] target) {
		double sum = 0;
		for (int i = 0; i < actual.length; i++) {
			sum += square(target[i] - actual[i]);
		}
		return sum / actual.length;
	}

	public static boolean approxEqual(double value, double compare, double precision) {
		return Math.abs(value - compare) <= precision;
	}

	public static double[] simpleMovingAverage(double[] values, int windowSize) {
		double[] ma = new double[values.length - windowSize + 1];
		if (values.length % windowSize != 0)
			return ma;
		for (int i = windowSize - 1; i < values.length; i++) {
			double sum = 0;
			for (int j = 0; j < windowSize; j++) {
				sum += values[i - windowSize + 1 + j];
			}
			ma[i - windowSize + 1] = sum / windowSize;
		}
		return ma;
	}

	public static double[] sort(double[] values) {
		return toPrimitiveArray(toArrayList(values).stream().sorted().collect(Collectors.toList()));
	}

}
