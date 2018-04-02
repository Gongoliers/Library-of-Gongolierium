package com.thegongoliers.math.filter;

import com.thegongoliers.annotations.TestedBy;

@TestedBy(team = "5112", year = "2018")
public class LowPassFilter implements Filter{

    private double lastValue;

    private final double filterCoef;

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
