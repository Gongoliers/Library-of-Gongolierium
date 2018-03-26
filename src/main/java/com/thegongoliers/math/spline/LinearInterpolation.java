package com.thegongoliers.math.spline;

/**
 * A linear interpolation
 */
public class LinearInterpolation implements Spline {

    private double p0, p1, totalTime;

    /**
     * Creates a linear interpolation from p0 to p1. The total time is 1 second.
     *
     * @param p0 The initial position.
     * @param p1 The final position.
     */
    public LinearInterpolation(double p0, double p1) {
        this(p0, p1, 1.0);
    }

    /**
     * Creates a linear interpolation from p0 to p1.
     *
     * @param p0        The initial position.
     * @param p1        The final position.
     * @param totalTime The total time of the spline.
     */
    public LinearInterpolation(double p0, double p1, double totalTime) {
        this.p0 = p0;
        this.p1 = p1;
        this.totalTime = totalTime;
    }

    @Override
    public double calculate(double time) {
        time /= totalTime;
        return p0 + p1 * time;
    }
}
