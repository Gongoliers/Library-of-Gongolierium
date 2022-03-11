package com.thegongoliers.output.motors;

import com.thegongoliers.annotations.Untested;
import com.thegongoliers.math.GMath;

@Untested
public class PowerEfficiencyMotorModule implements MotorModule {

    private final double mSecondsToReachFullSpeed;

    public PowerEfficiencyMotorModule(double secondsToReachFullSpeed){
        mSecondsToReachFullSpeed = secondsToReachFullSpeed;
    }

    @Override
    public double run(double currentSpeed, double desiredSpeed, double deltaTime) {
        double maximumRate = getMaxRate(deltaTime);
        return GMath.rateLimit(maximumRate, desiredSpeed, currentSpeed);
    }

    private double getMaxRate(double deltaTime) {
        return mSecondsToReachFullSpeed == 0 ? 1 : deltaTime / mSecondsToReachFullSpeed;
    }
}
