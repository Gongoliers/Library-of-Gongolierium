package com.thegongoliers.math;

/**
 * Created by Kyle on 7/9/2017.
 */
public class ExponentialMovingAverage {

    private double initialValue = 0.0;
    private int period;
    private double multiplier;
    private int count = 0;

    public ExponentialMovingAverage(int period) {
        this.period = period;
        multiplier = 2 / (period + 1.0);
    }

    public double calculate(double input) {
        if (count < period) {
            initialValue += input / period;
            count++;
        }

        if (count > period) {
            initialValue = (input - initialValue) * multiplier + initialValue;
            return initialValue;
        } else {
            if (count == period) {
                count++;
                return initialValue;
            }
            return input;
        }

    }

}
