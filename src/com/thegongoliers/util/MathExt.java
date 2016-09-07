package com.thegongoliers.util;

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

	/**
	 * toPolar : Position -> PolarCoordinate
	 * 
	 * Converts a cartesian coordinate to the polar plane.
	 * 
	 * @param cartesian
	 * @return
	 */
	public static PolarCoordinate toPolar(Position cartesian) {
		double magnitude = Math.sqrt(Math.pow(cartesian.getX(), 2) + Math.pow(cartesian.getY(), 2));
		double angle = Math.atan2(cartesian.getY(), cartesian.getX());

		return new PolarCoordinate(magnitude, angle);
	}

	/**
	 * toCartesian : PolarCoordinate -> Position
	 * 
	 * Converts a polar coordinate to the cartesian plane.
	 * 
	 * @param polar
	 * @return
	 */
	public static Position toCartesian(PolarCoordinate polar) {
		double x = Math.cos(polar.getAngle()) * polar.getMagnitude();
		double y = Math.sin(polar.getAngle()) * polar.getMagnitude();
		return new Position(x, y);
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
		Function fn = new Function() {

			@Override
			public double function(double x) {
				return x / max;
			}

		};
		return map(values, fn);
	}

	public static double[] map(double[] values, Function function) {
		double[] mappedValues = new double[values.length];
		for (int i = 0; i < values.length; i++) {
			mappedValues[i] = function.function(values[i]);
		}
		return mappedValues;
	}

	public static double average(double[] values) {
		if (values.length == 0) {
			return Double.POSITIVE_INFINITY;
		}
		return sum(values) / values.length;
	}

	public static double first(double[] values) {
		return values[0];
	}

	public static double[] rest(double[] values) {
		double[] restValues = new double[values.length - 1];
		System.arraycopy(values, 1, restValues, 0, restValues.length);
		return restValues;
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

}
