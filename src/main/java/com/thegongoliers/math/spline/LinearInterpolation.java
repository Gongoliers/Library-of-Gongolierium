package com.thegongoliers.math.spline;

/**
 * A linear interpolation
 */
public class LinearInterpolation implements Spline {

    private final double slope;
    private final double intercept;


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
        slope = (p1 - p0) / totalTime;
        intercept = p0;
    }

    @Override
    public double calculate(double time) {
        return intercept + slope * time;
    }

    public double integral(double time){
        return intercept * time + slope / 2 * Math.pow(time, 2);
    }

    public double derivative(double time){
        return slope;
    }

    public double doubleDerivative(double time){
        return 0;
    }
}
