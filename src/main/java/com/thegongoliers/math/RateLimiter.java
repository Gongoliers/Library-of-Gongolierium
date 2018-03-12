package com.thegongoliers.math;

public class RateLimiter implements Filter{

    private double maxRate;
    private double lastValue;

    public RateLimiter(double maxRate, double lastValue){
        this.maxRate = maxRate;
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
