package com.thegongoliers.output.drivetrain;

import com.thegongoliers.math.GMath;

/**
 * A drivetrain module which will force the drivetrain to accelerate slower. 
 */
public class PowerEfficiencyModule extends BaseDriveModule {

    /**
     * The name of the module
     */
    public static final String NAME = "Power Efficiency";

    private static final double DEFAULT_TURN_THRESHOLD = 2.0;

    private double mRampingTime;
    private double mTurnThreshold;

    /**
     * Default constructor
     * @param secondsToReachFullSpeed the ramping time in seconds from 0 to full speed
     * @param turnThreshold the maximum difference between the two wheel speeds to run the power efficiency module on. Defaults to 2.
     */
    public PowerEfficiencyModule(double secondsToReachFullSpeed, double turnThreshold){
        super();
        setRampingTime(secondsToReachFullSpeed);
        setTurnThreshold(turnThreshold);
    }

    /**
     * Default constructor
     * @param secondsToReachFullSpeed the ramping time in seconds from 0 to full speed
     */
    public PowerEfficiencyModule(double secondsToReachFullSpeed){
        this(secondsToReachFullSpeed, DEFAULT_TURN_THRESHOLD);
    }

    /**
     * @param secondsToReachFullSpeed the ramping time in seconds from 0 to full speed
     */
    public void setRampingTime(double secondsToReachFullSpeed){
        if (secondsToReachFullSpeed < 0) throw new IllegalArgumentException("Seconds to reach full speed must be non-negative");
        mRampingTime = secondsToReachFullSpeed;
    }

    /**
     * @param turnThreshold the maximum difference between the two wheel speeds to run the power efficiency module on. Defaults to 2.
     */
    public void setTurnThreshold(double turnThreshold){
        if (turnThreshold < 0) throw new IllegalArgumentException("Turn threshold must be non-negative");
        mTurnThreshold = turnThreshold;
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
        if (shouldApplyRateLimit(desiredSpeed)){
            return desiredSpeed;
        }

        double maximumRate = getMaxRate(deltaTime);
        return applyRateLimit(currentSpeed, desiredSpeed, maximumRate);
    }

    private boolean shouldApplyRateLimit(DriveSpeed speed) {
        return Math.abs(speed.getLeftSpeed() - speed.getRightSpeed()) >= mTurnThreshold;
    }

    private double getMaxRate(double deltaTime) {
        return mRampingTime == 0 ? 1 : deltaTime / mRampingTime;
    }

    private DriveSpeed applyRateLimit(DriveSpeed lastSpeed, DriveSpeed desiredSpeed, double maxRate){
        double leftSpeed = GMath.rateLimit(maxRate, desiredSpeed.getLeftSpeed(), lastSpeed.getLeftSpeed());
        double rightSpeed = GMath.rateLimit(maxRate, desiredSpeed.getRightSpeed(), lastSpeed.getRightSpeed());
        return new DriveSpeed(leftSpeed, rightSpeed);
    }

    @Override
    public String getName() {
        return NAME;
    }

}