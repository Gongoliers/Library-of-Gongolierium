package com.thegongoliers.math;

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

}
