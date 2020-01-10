package com.thegongoliers.output.drivetrain;

import com.thegongoliers.math.GMath;

/**
 * A drivetrain module which will force the drivetrain to accelerate slower. 
 */
public class PowerEfficiencyModule extends BaseDriveModule {

    /**
     * The ramping strength from 0 to 1 (0 provides no constraints, 1 prevents drivetrain from accelerating)
     * Type: double
     */
    public static final String VALUE_STRENGTH = "strength";

    /**
     * The maximum difference between the two wheel speeds to run the power efficiency module on. Defaults to 2.
     * Type: double
     */
    public static final String VALUE_TURN_THRESHOLD = "turn_threshold";

    /**
     * The name of the module
     */
    public static final String NAME = "Power Efficiency";

    /**
     * Default constructor
     * @param strength the ramping strength from 0 to 1 (represents time in seconds from 0 to full speed)
     * @param turnThreshold the maximum difference between the two wheel speeds to run the power efficiency module on. Defaults to 2.
     */
    public PowerEfficiencyModule(double strength, double turnThreshold){
        super();
        values.put(VALUE_STRENGTH, strength);
        values.put(VALUE_TURN_THRESHOLD, turnThreshold);
    }

    /**
     * Default constructor
     * @param strength the ramping strength from 0 to 1 (represents time in seconds from 0 to full speed)
     */
    public PowerEfficiencyModule(double strength){
        this(strength, 2.0);
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
        double strength = (double) getValue(VALUE_STRENGTH);
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