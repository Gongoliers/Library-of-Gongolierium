package com.thegongoliers.math.filter;

public class HighPassFilter implements Filter {

    private double filterCoef;

    private double lastOutput;
    private double lastInput;

    public HighPassFilter(double lastInput) {
        this(lastInput, 0.0, 0.0);
    }

    public HighPassFilter(double filterCoef, double lastInput, double lastOutput) {
        this.filterCoef = filterCoef;
        this.lastOutput = lastOutput;
        this.lastInput = lastInput;
    }

    @Override
    public double filter(double value) {
        double filteredValue = filterCoef * (lastOutput + value - lastInput);
        lastOutput = filteredValue;
        lastInput = value;

        return filteredValue;
    }
}
