package com.thegongoliers.math;

import com.thegongoliers.math.exceptions.OutOfBoundsException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MathExt {

    private MathExt() {
    }

    /**
     * isOdd : int -> boolean
     * <p>
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
     * <p>
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
     * @param values The value to calculate the magnitude of.
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
     * Determines the sign of a value
     * <p>
     * Produces 1 if the value if positive, -1 if it is negative, and 0 if it is
     * 0
     *
     * @param value The value which to get the sign from
     * @return 1 if the value if positive, -1 if it is negative, and 0 if it is
     * 0
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
     * @param value   The initial value
     * @param percent The percent of the value to keep
     * @return The percent of the initial value
     */
    public static double percent(double value, double percent) {
        return value * percent / 100.0;
    }

    /**
     * Calculates the square of some value
     *
     * @param value The base value
     * @return The square of value
     */
    public static double square(double value) {
        return Math.pow(value, 2);
    }

    /**
     * Normalizes a value to the given range.
     *
     * @param value The value to normalize.
     * @param min   The min of the range.
     * @param max   The max of the range.
     * @return The normalized value in the range.
     */
    public static double normalize(double value, double min, double max) {
        if (value > max || value < min){
            throw new OutOfBoundsException();
        }
        return (value - min) / (max - min);
    }

    /**
     * Normalize the values to the max of the array.
     *
     * @param values The values to normalize.
     * @return The normalized values.
     */
    public static double[] normalize(double[] values) {
        double max = max(values);
        return toPrimitiveArray(toList(values).stream().map(x -> x / max).collect(Collectors.toList()));
    }

    /**
     * Converts an array to a list.
     *
     * @param values The array to convert.
     * @return The list containing all of the array values.
     */
    public static List<Double> toList(double[] values) {
        ArrayList<Double> arrVals = new ArrayList<>();
        for (double value : values) {
            arrVals.add(value);
        }
        return arrVals;
    }

    /**
     * Converts a list to an array.
     *
     * @param values The list to convert.
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
     * @param values The array to sum.
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
     * @param values The array.
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
     * @param values The array.
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
     * @param value The value to constrain.
     * @param min   The min of the range.
     * @param max   The max of the range.
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
     * @param value The value to round.
     * @return The rounded value as an int.
     */
    public static int roundToInt(double value) {
        return (int) Math.round(value);
    }

    /**
     * Round a value to the given number of decimal places.
     *
     * @param value     The value to round.
     * @param numPlaces The number of decimal places to round to.
     * @return The rounded value.
     */
    public static double roundPlaces(double value, int numPlaces) {
        double multiplier = Math.pow(10, numPlaces);
        return Math.round(value * multiplier) / multiplier;
    }

    /**
     * Snap a value to the nearest multiple of the nearest parameter.
     *
     * @param value   The value to snap.
     * @param nearest The value to snap to.
     * @return The value snapped to the nearest multiple of nearest.
     */
    public static double snap(double value, double nearest) {
        return Math.round(value / nearest) * nearest;
    }

    /**
     * Determines if a number is divisible by another number.
     *
     * @param value   The numerator.
     * @param divisor The denominator.
     * @return True if the value is divisible by the divider.
     */
    public static boolean divisibleBy(int value, int divisor) {
        return value % divisor == 0;
    }

    /**
     * Determines if a value is approximately equal to another value.
     *
     * @param value     The value to compare.
     * @param compare   The value to compare.
     * @param precision The precision of the equality.
     * @return True if the value is equal to the compare value with the given
     * precision.
     */
    public static boolean approxEqual(double value, double compare, double precision) {
        return Math.abs(value - compare) <= precision;
    }

    /**
     * Get the slope from the angle in degrees.
     *
     * @param angle The angle in degrees.
     * @return The slope of the line which the angle creates.
     */
    public static double slopeFromAngleDegrees(double angle) {
        return slopeFromAngleRadians(Math.toRadians(angle));
    }

    /**
     * Get the slope from the angle in radians.
     *
     * @param angle The angle in radians.
     * @return The slope of the line which the angle creates.
     */
    public static double slopeFromAngleRadians(double angle) {
        return Math.tan(angle);
    }

    /**
     * Limit the max rate of increase.
     * @param max The maximum rate allowed.
     * @param value The current input value.
     * @param lastValue The last value returned by this function.
     * @return The updated input value.
     */
    public static double rateLimit(double max, double value, double lastValue){
        double delta = value - lastValue;
        double signOfDelta = sign(delta);
        double newValue = value;
        if(Math.abs(delta) >= max){
            newValue = lastValue + signOfDelta * max;
        }
        return newValue;
    }


    /**
     * Computes the standard deviation of a variable.
     * @param x The set of data.
     * @return The standard deviation of x.
     */
    public static double standardDeviation(double[] x){
        if(x == null || x.length < 2){
            return 0;
        }
        double mean = mean(x);
        double variance = 0;
        for (double val: x){
            variance += Math.pow(val - mean, 2);
        }
        variance /= (x.length - 1);
        return Math.sqrt(variance);
    }

    /**
     * Computes the correlation between two variables.
     * @param x The first set of data.
     * @param y The second set of data.
     * @return The Pearson correlation from -1 to 1
     */
    public static double correlation(double[] x, double[] y){
        if(x == null || y == null){
            return 0;
        }

        if(x.length != y.length){
            throw new UnequalLengthException();
        }

        double xSum = 0;
        double ySum = 0;
        double x2Sum = 0;
        double y2Sum = 0;
        double xySum = 0;
        int n = x.length;

        for (int i = 0; i < n; i++) {
            xSum += x[i];
            ySum += y[i];
            x2Sum += Math.pow(x[i], 2);
            y2Sum += Math.pow(y[i], 2);
            xySum += x[i] * y[i];
        }

        double top = n * xySum - xSum * ySum;
        double bottom = Math.sqrt(n * x2Sum - Math.pow(xSum, 2)) * Math.sqrt(n * y2Sum - Math.pow(ySum, 2));
        if (bottom == 0){
            return 0;
        }
        return top / bottom;
    }

    /**
     * Computes the mean of a variable.
     * @param x The set of data
     * @return The mean of x.
     */
    public static double mean(double[] x) {
        if(x == null || x.length == 0){
            return 0;
        }
        double sum = 0;
        for (double val: x){
            sum += val;
        }
        return sum / x.length;
    }

    public static class UnequalLengthException extends RuntimeException {
        public UnequalLengthException(){
            super("Lengths are not equal");
        }
    }
}
