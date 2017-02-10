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
	 * Calculate the magnitude of the given values.
	 * 
	 * @param values
	 *            The value to calculate the magnitude of.
	 * @return The magnitude of the values.
	 */
	public static double magnitude(double... values) {
		double squaredSum = 0;
		for (double val : values) {
			squaredSum += square(val);
		}
		return Math.sqrt(squaredSum);
	}

	/**
	 * Convert a point from the cartesian plane to the spherical plane.
	 * 
	 * @param p
	 *            The point in cartesian.
	 * @return The point in spherical.
	 */
	public static Spherical toSpherical(Point p) {
		double rho = Math.sqrt(square(p.x) + square(p.y) + square(p.z));
		double theta = Math.atan2(p.y, p.x);
		double phi = Math.acos(p.z / rho);
		return new Spherical(rho, theta, phi);
	}

	/**
	 * Convert a point from the cylindrical plane to the spherical plane.
	 * 
	 * @param p
	 *            The point in cylindrical.
	 * @return The point in spherical.
	 */
	public static Spherical toSpherical(Cylindrical c) {
		double rho = Math.sqrt(square(c.r) + square(c.z));
		double phi = Math.asin(c.r / rho);
		return new Spherical(rho, c.theta, phi);
	}

	/**
	 * Convert a point from the cartesian plane to the cylindrical plane.
	 * 
	 * @param p
	 *            The point in cartesian.
	 * @return The point in cylindrical.
	 */
	public static Cylindrical toCylindrical(Point p) {
		double magnitude = Math.sqrt(Math.pow(p.x, 2) + Math.pow(p.y, 2));
		double angle = Math.atan2(p.y, p.x);
		return new Cylindrical(magnitude, angle, p.z);
	}

	/**
	 * Convert a point from the spherical plane to the cylindrical plane.
	 * 
	 * @param p
	 *            The point in spherical.
	 * @return The point in cylindrical.
	 */
	public static Cylindrical toCylindrical(Spherical s) {
		return new Cylindrical(s.p * Math.sin(s.phi), s.theta, s.p * Math.cos(s.phi));
	}

	/**
	 * Convert a point from the cylindrical plane to the cartesian plane.
	 * 
	 * @param p
	 *            The point in cylindrical.
	 * @return The point in cartesian.
	 */
	public static Point toCartesian(Cylindrical c) {
		return new Point(c.r * Math.cos(c.theta), c.r * Math.sin(c.theta), c.z);
	}

	/**
	 * Convert a point from the spherical plane to the cartesian plane.
	 * 
	 * @param p
	 *            The point in spherical.
	 * @return The point in cartesian.
	 */
	public static Point toCartesian(Spherical s) {
		return new Point(s.p * Math.sin(s.phi) * Math.cos(s.theta), s.p * Math.sin(s.phi) * Math.sin(s.theta),
				s.p * Math.cos(s.phi));
	}

	/**
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
	 * Calculates the square of some value
	 * 
	 * @param value
	 *            The base value
	 * @return The square of value
	 */
	public static double square(double value) {
		return Math.pow(value, 2);
	}

	/**
	 * Normalizes a value to the given range.
	 * 
	 * @param value
	 *            The value to normalize.
	 * @param min
	 *            The min of the range.
	 * @param max
	 *            The max of the range.
	 * @return The normalized value in the range.
	 */
	public static double normalize(double value, double min, double max) {
		return (value - min) / (max - min);
	}

	/**
	 * Normalize the values to the max of the array.
	 * 
	 * @param values
	 *            The values to normalize.
	 * @return The normalized values.
	 */
	public static double[] normalize(double[] values) {
		double max = max(values);
		return toPrimitiveArray(toList(values).stream().map(x -> x / max).collect(Collectors.toList()));
	}

	/**
	 * Converts an array to a list.
	 * 
	 * @param values
	 *            The array to convert.
	 * @return The list containing all of the array values.
	 */
	public static List<Double> toList(double[] values) {
		ArrayList<Double> arrVals = new ArrayList<>();
		for (int i = 0; i < values.length; i++) {
			arrVals.add(values[i]);
		}
		return arrVals;
	}

	/**
	 * Converts a list to an array.
	 * 
	 * @param values
	 *            The list to convert.
	 * @return The array containing all of the list values.
	 */
	public static double[] toPrimitiveArray(List<Double> values) {
		double[] primValues = new double[values.size()];
		for (int i = 0; i < primValues.length; i++) {
			primValues[i] = values.get(i);
		}
		return primValues;
	}

	/**
	 * Calculate the sum of an array.
	 * 
	 * @param values
	 *            The array to sum.
	 * @return The sum of the array.
	 */
	public static double sum(double[] values) {
		double total = 0;
		for (double value : values) {
			total += value;
		}
		return total;
	}

	/**
	 * Find the min of the array.
	 * 
	 * @param values
	 *            The array.
	 * @return The min value of the array.
	 */
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

	/**
	 * Find the max of the array.
	 * 
	 * @param values
	 *            The array.
	 * @return The max value of the array.
	 */
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

	/**
	 * Constrain a value to the range, where if the value is out of the range it
	 * is converted to the nearest range bound.
	 * 
	 * @param value
	 *            The value to constrain.
	 * @param min
	 *            The min of the range.
	 * @param max
	 *            The max of the range.
	 * @return The value which is constrained to the range.
	 */
	public static double toRange(double value, double min, double max) {
		double newVal = Math.max(min, value);
		newVal = Math.min(newVal, max);
		return newVal;
	}

	/**
	 * Round a value to the nearest int.
	 * 
	 * @param value
	 *            The value to round.
	 * @return The rounded value as an int.
	 */
	public static int roundToInt(double value) {
		return (int) Math.round(value);
	}

	/**
	 * Round a value to the given number of decimal places.
	 * 
	 * @param value
	 *            The value to round.
	 * @param numPlaces
	 *            The number of decimal places to round to.
	 * @return The rounded value.
	 */
	public static double roundPlaces(double value, int numPlaces) {
		double multiplier = Math.pow(10, numPlaces);
		return snap(value, 1 / multiplier);
	}

	/**
	 * Snap a value to the nearest multiple of the nearest parameter.
	 * 
	 * @param value
	 *            The value to snap.
	 * @param nearest
	 *            The value to snap to.
	 * @return The value snapped to the nearest multiple of nearest.
	 */
	public static double snap(double value, double nearest) {
		return Math.round(value / nearest) * nearest;
	}

	/**
	 * Determines if a number is divisible by another number.
	 * 
	 * @param value
	 *            The numerator.
	 * @param divisor
	 *            The denominator.
	 * @return True if the value is divisible by the divider.
	 */
	public static boolean divisibleBy(int value, int divisor) {
		return value % divisor == 0;
	}

	/**
	 * Determines if a value is approximately equal to another value.
	 * 
	 * @param value
	 *            The value to compare.
	 * @param compare
	 *            The value to compare.
	 * @param precision
	 *            The precision of the equality.
	 * @return True if the value is equal to the compare value with the given
	 *         precision.
	 */
	public static boolean approxEqual(double value, double compare, double precision) {
		return Math.abs(value - compare) <= precision;
	}

}
