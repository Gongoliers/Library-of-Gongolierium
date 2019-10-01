package com.thegongoliers.math.filter;

import com.thegongoliers.annotations.UsedInCompetition;
import com.thegongoliers.math.GMath;

@UsedInCompetition(team = "5112", year = "2018")
public class LowPassFilter implements Filter{

    private double lastValue;

    private double filterCoef;

    public LowPassFilter(double filterCoef) {
        this(filterCoef, 0.0);
    }

    public LowPassFilter(double filterCoef, double initialValue) {
        this.filterCoef = GMath.clamp01(filterCoef);
        this.lastValue = initialValue;
    }

    /**
     * Set the filter coefficient.
     * @param filterCoef The filter coefficient between 0 and 1.
     */
    public void setFilterCoefficient(double filterCoef){
        this.filterCoef = filterCoef;
    }

    @Override
    public double filter(double value) {
        double filteredValue = filterCoef * lastValue + (1 - filterCoef) * value;
        lastValue = filteredValue;
        return filteredValue;
    }
}
