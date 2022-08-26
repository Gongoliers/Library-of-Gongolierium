package com.thegongoliers.output.drivetrain.swerve;

import com.thegongoliers.math.GMath;
import com.thegongoliers.utils.IModule;

/**
 * A drivetrain module which will force the drivetrain to accelerate slower.
 */
public class RampSwerveModule implements IModule<SwerveSpeed> {
    private double mRampingTime;

    /**
     * Default constructor
     *
     * @param secondsToReachFullSpeed the ramping time in seconds from 0 to full speed
     */
    public RampSwerveModule(double secondsToReachFullSpeed) {
        setRampingTime(secondsToReachFullSpeed);
    }

    @Override
    public SwerveSpeed run(SwerveSpeed currentSpeed, SwerveSpeed desiredSpeed, double deltaTime) {
        double maximumRate = getMaxRate(deltaTime);
        return applyRateLimit(currentSpeed, desiredSpeed, maximumRate);
    }

    /**
     * @param secondsToReachFullSpeed the ramping time in seconds from 0 to full speed
     */
    public void setRampingTime(double secondsToReachFullSpeed) {
        if (secondsToReachFullSpeed < 0)
            throw new IllegalArgumentException("Seconds to reach full speed must be non-negative");
        mRampingTime = secondsToReachFullSpeed;
    }

    private double getMaxRate(double deltaTime) {
        return mRampingTime == 0 ? 1 : deltaTime / mRampingTime;
    }

    private SwerveSpeed applyRateLimit(SwerveSpeed lastSpeed, SwerveSpeed desiredSpeed, double maxRate) {
        double x = GMath.rateLimit(maxRate, desiredSpeed.getX(), lastSpeed.getX());
        double y = GMath.rateLimit(maxRate, desiredSpeed.getY(), lastSpeed.getY());
        double rotation = GMath.rateLimit(maxRate, desiredSpeed.getRotation(), lastSpeed.getRotation());
        return new SwerveSpeed(x, y, rotation);
    }
}