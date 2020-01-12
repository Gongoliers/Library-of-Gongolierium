package com.thegongoliers.math;

import com.thegongoliers.annotations.UsedInCompetition;

public class GMath {

    private GMath() {
    }

    /**
     * Calculate the magnitude of the given values.
     *
     * @param values The value to calculate the magnitude of.
     * @return The magnitude of the values.
     */
    public static double magnitude(double... values) { // TODO: Move this into the vector class
        double squaredSum = 0;
        for (double val : values) {
            squaredSum += Math.pow(val, 2);
        }
        return Math.sqrt(squaredSum);
    }

    /**
     * Determines the sign of a value
     *
     * @param value The value which to get the sign from
     * @return 1 if the value is positive or 0, -1 if it is negative
     * 0
     */
    public static int sign(double value) {
        return value >= 0 ? 1 : -1;
    }


    /**
     * Calculates a value to some power, but preserves the sign if it is negative.
     * @param value the value to raise to a power
     * @param power the power to raise the value by
     * @return the value to the power, with its sign preserved
     */
    @UsedInCompetition(team = "5112", year = "2015")
    public static double signPreservingPower(double value, double power){
        return Math.copySign(Math.pow(value, power), value);
    }

    /**
     * Calculates the inverse linear interpolation of a value within the range [a, b]
     * @param a The minimum of the range
     * @param b The maximum of the range
     * @param value The value between a and b
     * @return the percentage of value between a and b
     */
    public static double inverseLerp(double a, double b, double value){
        if (approximately(a, b)) return 0;
        return clamp01((value - a) / (b - a));
    }

    /**
     * Calculates the linear interpolation of t into the range [a, b]
     * @param a The minimum of the range
     * @param b The maximum of the range
     * @param t the percentage of value between a and b (between 0 and 1)
     * @return the interpolated value between a and b
     */
    public static double lerp(double a, double b, double t){
        return lerpUnclamped(a, b, GMath.clamp01(t));
    }

    /**
     * Calculates the linear interpolation of t into the range [a, b]
     * @param a The minimum of the range
     * @param b The maximum of the range
     * @param t the percentage of value between a and b
     * @return the interpolated value between a and b
     */
    public static double lerpUnclamped(double a, double b, double t){
        double range = b - a;
        return range * t + a;
    }

    /**
     * Calculates the linear interpolation of t into the range [a, b], wraps correctly around 360 degrees
     * @param a The minimum of the range (degrees)
     * @param b The maximum of the range (degrees)
     * @param t the percentage of value between a and b (between 0 and 1)
     * @return the interpolated value between a and b
     */
    public static double lerpAngle(double a, double b, double t){ // TODO: Test
        double n = repeat(b - a, 360);
        if (n > 180){
            n -= 360;
        }
        return a + n * clamp01(t);
    }

    /**
     * Interpolates between from and to with smoothing at the limits.
     * @param from the starting value
     * @param to the ending value
     * @param t the percentage of value between from and to (between 0 and 1)
     * @return the interpolated value between from and to
     */
    public static double smoothStep(double from, double to, double t){
        // TODO
        return 0;
    }

    /**
     * Moves a value toward a target without exceeding maxDelta
     * @param current the current value
     * @param target the target value
     * @param maxDelta the maximum delta
     * @return the updated value
     */
    public static double moveTowards(double current, double target, double maxDelta){
        double delta = target - current;
        if (Math.abs(delta) <= maxDelta){
            return target;
        }
        return current + maxDelta * sign(delta);
    }

    /**
     * PingPongs the value t, so that it is never larger than length and never smaller than 0.
     * @param t the value to ping pong
     * @param length the length of the ping pong
     * @return the value which will move back and forth between 0 and length
     */
    public static double pingPong(double t, double length){ // TODO: Add tests
        t = repeat(t, length * 2);
        return length - Math.abs(t - length);
    }

    /**
     * Loops the value t, so that it is never larger than length and never smaller than 0. Does not work on negative numbers.
     * @param t the value to loop
     * @param length the length of the loop
     * @return the 'modulus' of t and length 
     */
    public static double repeat(double t, double length){
        return t - Math.floor(t / length) * length;
    }

    /**
     * Deadbands an input (makes 0 if below threshold)
     * @param value the input value
     * @param threshold the deadband threshold
     * @return the thresholded input
     */
    public static double deadband(double value, double threshold){
        if (Math.abs(value) < threshold) return 0;
        return value;
    }

    /**
     * Calculate the sum of an array.
     *
     * @param values The array to sum.
     * @return The sum of the array.
     */
    public static double sum(double... values) {
        double total = 0;
        for (double value : values) {
            total += value;
        }
        return total;
    }

    /**
     * Find the minimum value
     *
     * @param values The value
     * @return The min value
     */
    public static double min(double... values) {
        if (values.length == 0){
            return 0;
        }
        double min = values[0];
        for (double value: values){
            if (value < min){
                min = value;
            }
        }
        return min;
    }

    /**
     * Find the maximum value
     *
     * @param values The values
     * @return The max value
     */
    public static double max(double... values){
        if (values.length == 0){
            return 0;
        }
        double max = values[0];
        for (double value: values){
            if (value > max){
                max = value;
            }
        }
        return max;
    }

    /**
     * Find the minimum value
     *
     * @param values The value
     * @return The min value
     */
    public static int min(int... values) { // TODO: Test
        if (values.length == 0){
            return 0;
        }
        int min = values[0];
        for (int value: values){
            if (value < min){
                min = value;
            }
        }
        return min;
    }

    /**
     * Find the maximum value
     *
     * @param values The values
     * @return The max value
     */
    public static int max(int... values){ // TODO: Test
        if (values.length == 0){
            return 0;
        }
        int max = values[0];
        for (int value: values){
            if (value > max){
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
    public static double clamp(double value, double min, double max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    public static int clamp(int value, int min, int max){
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

     /**
     * Constrain a value to be within 0 and 1
     *
     * @param value The value to constrain.
     * @return The value which is between 0 and 1
     */
    public static double clamp01(double value){
        return clamp(value, 0, 1);
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
        if (divisor == 0){
            return false;
        }
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
    public static boolean approximately(double value, double compare, double precision) {
        return Math.abs(value - compare) <= precision;
    }

    /**
     * Determines if a value is approximately equal to another value.
     *
     * @param a     The value to compare.
     * @param b   The value to compare.
     * @return True if a and b are equal to within epsilon.
     */
    public static boolean approximately(double a, double b){
        return Math.abs(b - a) < Math.max(1e-6 * Math.max(Math.abs(a), Math.abs(b)), Double.MIN_VALUE * 8);
    }

    /**
     * Calculates the shortest difference between two angles in degrees.
     * @param current The current angle in degrees
     * @param target The target angle in degrees
     * @return the shortest difference between the angles in degrees
     */
    public static double deltaAngle(double current, double target){
        double delta = target - current;
        delta += 180;
        delta = delta - Math.floor(delta / 360) * 360;
        delta -= 180;
        if (approximately(Math.abs(delta), 180)){
            return 180;
        }
        return delta;
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
            return Double.NaN;
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
            return Double.NaN;
        }
        double sum = 0;
        for (double val: x){
            sum += val;
        }
        return sum / x.length;
    }

    public static class UnequalLengthException extends RuntimeException {

        private static final long serialVersionUID = -7924653865358440946L;

        public UnequalLengthException() {
            super("Lengths are not equal");
        }
    }
}
