package com.thegongoliers.output.motors;

import com.thegongoliers.annotations.Untested;
import com.thegongoliers.math.GMath;

@Untested
public class SpeedConstraintMotorModule implements MotorModule {

    private final double mMaxSpeed;
    private final boolean mShouldScaleSpeeds;

    public SpeedConstraintMotorModule(double maxSpeed, boolean shouldScaleSpeeds) {
        mMaxSpeed = maxSpeed;
        mShouldScaleSpeeds = shouldScaleSpeeds;
    }

    @Override
    public Double run(Double currentSpeed, Double desiredSpeed, double deltaTime) {
        if (mShouldScaleSpeeds) {
            return desiredSpeed * mMaxSpeed;
        } else {
            return GMath.clamp(desiredSpeed, -mMaxSpeed, mMaxSpeed);
        }
    }
}
