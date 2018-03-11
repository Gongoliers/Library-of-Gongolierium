package com.thegongoliers.math;

public class LowPassFilter implements Filter{

    private double lastValue;

    private double filterCoef;

    public LowPassFilter(double filterCoef) {
        this(filterCoef, 0.0);
    }

    public LowPassFilter(double filterCoef, double initialValue) {
        this.filterCoef = filterCoef;
        this.lastValue = initialValue;
    }

    @Override
    public double filter(double value) {
        double filteredValue = filterCoef * lastValue + (1 - filterCoef) * value;
        lastValue = filteredValue;
        return filteredValue;
    }
}
