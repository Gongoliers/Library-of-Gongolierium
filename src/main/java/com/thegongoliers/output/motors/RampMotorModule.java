package com.thegongoliers.output.motors;

import com.thegongoliers.annotations.Untested;
import com.thegongoliers.math.GMath;

@Untested
public class RampMotorModule implements MotorModule {

    private final double mSecondsToReachFullSpeed;

    public RampMotorModule(double secondsToReachFullSpeed){
        mSecondsToReachFullSpeed = secondsToReachFullSpeed;
    }

    @Override
    public Double run(Double currentSpeed, Double desiredSpeed, double deltaTime) {
        double maximumRate = getMaxRate(deltaTime);
        return GMath.rateLimit(maximumRate, desiredSpeed, currentSpeed);
    }

    private double getMaxRate(double deltaTime) {
        return mSecondsToReachFullSpeed == 0 ? 1 : deltaTime / mSecondsToReachFullSpeed;
    }
}
