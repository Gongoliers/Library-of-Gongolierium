package com.thegongoliers.math.filter;

import com.thegongoliers.annotations.TestedBy;
import com.thegongoliers.math.MathExt;

@TestedBy(team = "5112", year = "2018")
public class RateLimiter implements Filter {

    private final double maxRate;
    private double lastValue;

    public RateLimiter(double maxRate, double lastValue){
        this.maxRate = Math.abs(maxRate);
        this.lastValue = lastValue;
    }

    public RateLimiter(double maxRate){
        this(maxRate, 0.0);
    }

    @Override
    public double filter(double value) {

        double filtered = MathExt.rateLimit(maxRate, value, lastValue);

        this.lastValue = filtered;

        return filtered;
    }

}
