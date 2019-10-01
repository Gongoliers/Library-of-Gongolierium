package com.thegongoliers.math.filter;

import com.thegongoliers.annotations.UsedInCompetition;
import com.thegongoliers.math.GMath;

@UsedInCompetition(team = "5112", year = "2018")
public class RateLimiter implements Filter {

    private double maxRate;
    private double lastValue;

    public RateLimiter(double maxRate, double lastValue){
        this.maxRate = Math.abs(maxRate);
        this.lastValue = lastValue;
    }

    public RateLimiter(double maxRate){
        this(maxRate, 0.0);
    }

    /**
     * Set the max rate.
     * @param maxRate The max rate.
     */
    public void setMaxRate(double maxRate){
        this.maxRate = maxRate;
    }

    @Override
    public double filter(double value) {

        double filtered = GMath.rateLimit(maxRate, value, lastValue);

        this.lastValue = filtered;

        return filtered;
    }

}
