package com.thegongoliers.math.spline;

/**
 * Represents a spline.
 */
public interface Spline {

    /**
     * Calculate the position of the spline at a given time.
     *
     * @param time The time in seconds of the spline.
     * @return The position of the spline at the given time.
     */
    double calculate(double time);

    /**
     * Compute the integral at the given time.
     * @param time The time in seconds of the spline.
     * @return The integral of the spline at the given time.
     */
    double integral(double time);

    /**
     * Compute the derivative at the given time.
     * @param time The time in seconds of the spline.
     * @return The derivative of the spline at the given time.
     */
    double derivative(double time);

    /**
     * Compute the double derivative at the given time.
     * @param time The time in seconds of the spline.
     * @return The double derivative of the spline at the test.
     */
    double doubleDerivative(double time);
}
