package com.thegongoliers.output.drivetrain;

import com.thegongoliers.math.GMath;

/**
 * A drivetrain module which will force the drivetrain to accelerate slower. 
 */
public class PowerEfficiencyModule extends BaseDriveModule {

    /**
     * The ramping time in seconds from 0 to full speed
     * Type: double
     */
    public static final String VALUE_RAMPING_TIME = "seconds_to_full_speed";

    /**
     * The maximum difference between the two wheel speeds to run the power efficiency module on. Defaults to 2.
     * Type: double
     */
    public static final String VALUE_TURN_THRESHOLD = "turn_threshold";

    /**
     * The name of the module
     */
    public static final String NAME = "Power Efficiency";

    private static final double DEFAULT_TURN_THRESHOLD = 2.0;

    /**
     * Default constructor
     * @param secondsToReachFullSpeed the ramping time in seconds from 0 to full speed
     * @param turnThreshold the maximum difference between the two wheel speeds to run the power efficiency module on. Defaults to 2.
     */
    public PowerEfficiencyModule(double secondsToReachFullSpeed, double turnThreshold){
        super();

        if (secondsToReachFullSpeed < 0) throw new IllegalArgumentException("Seconds to reach full speed must be non-negative");
        if (turnThreshold < 0) throw new IllegalArgumentException("Turn threshold must be non-negative");

        values.put(VALUE_RAMPING_TIME, secondsToReachFullSpeed);
        values.put(VALUE_TURN_THRESHOLD, turnThreshold);
    }

    /**
     * Default constructor
     * @param secondsToReachFullSpeed the ramping time in seconds from 0 to full speed
     */
    public PowerEfficiencyModule(double secondsToReachFullSpeed){
        this(secondsToReachFullSpeed, DEFAULT_TURN_THRESHOLD);
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
        double threshold = (double) getValue(VALUE_TURN_THRESHOLD);
        return Math.abs(speed.getLeftSpeed() - speed.getRightSpeed()) >= threshold;
    }

    private double getMaxRate(double deltaTime) {
        double strength = (double) getValue(VALUE_RAMPING_TIME);
        return strength == 0 ? 1 : deltaTime / strength;
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