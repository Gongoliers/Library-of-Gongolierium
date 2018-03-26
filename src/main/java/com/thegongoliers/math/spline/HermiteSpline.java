package com.thegongoliers.math.spline;

/**
 * A Hermite 3rd order spline.
 */
public class HermiteSpline implements Spline{

    private double p0, p1, v0, v1, totalTime;

    /**
     * Create a Hermite 3rd order spline from p0 to p1 with the initial velocity of v0 and the final velocity of v1. The total time is 1 second.
     *
     * @param p0 The initial position.
     * @param v0 The initial velocity.
     * @param p1 The final position.
     * @param v1 The final velocity.
     */
    public HermiteSpline(double p0, double v0, double p1, double v1) {
        this(p0, v0, p1, v1, 1.0);
    }

    /**
     * Create a Hermite 3rd order spline from p0 to p1 with the initial velocity of v0 and the final velocity of v1.
     *
     * @param p0        The initial position.
     * @param v0        The initial velocity.
     * @param p1        The final position.
     * @param v1        The final velocity.
     * @param totalTime The total time of the spline.
     */
    public HermiteSpline(double p0, double v0, double p1, double v1, double totalTime) {
        this.p0 = p0;
        this.p1 = p1;
        this.v0 = v0;
        this.v1 = v1;
        this.totalTime = totalTime;
    }

    @Override
    public double calculate(double time) {
        time /= totalTime;
        double first = (2 * Math.pow(time, 3) - 3 * Math.pow(time, 2) + 1) * p0;
        double second = (Math.pow(time, 3) - 2 * Math.pow(time, 2) + time) * v0;
        double third = (-2 * Math.pow(time, 3) + 3 * Math.pow(time, 2)) * p1;
        double fourth = (Math.pow(time, 3) - Math.pow(time, 2)) * v1;
        return first + second + third + fourth;
    }

    public double calculateVelocity(double time){
        double dt = 0.0001;
        double pos = calculate(time);
        return (pos - calculate(time - dt)) / dt;
    }

    public double calculateAcceleration(double time){
        double dt = 0.0001;
        return (calculateVelocity(time + dt) - calculateVelocity(time)) / (2 * dt);
    }



}
