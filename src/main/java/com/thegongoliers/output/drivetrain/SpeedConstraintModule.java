package com.thegongoliers.output.drivetrain;

import com.thegongoliers.math.GMath;

/**
 * A drivetrain module which will constrain the maximum speed of the robot. 
 */
public class SpeedConstraintModule implements DriveModule {

    private double mMaxSpeed;
    private boolean shouldScaleSpeeds;

    /**
     * The name of the module
     */
    public static final String NAME = "Speed Constraint";

    /**
     * Default constructor
     * @param maxSpeed The maximum speed
     * @param shouldScaleSpeeds True if the speeds should be scaled to the clamped range, or false otherwise
     */
    public SpeedConstraintModule(double maxSpeed, boolean shouldScaleSpeeds){
        super();
        setMaxSpeed(maxSpeed);
        setShouldScaleSpeeds(shouldScaleSpeeds);
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
        if (shouldScaleSpeeds){
            return scaleSpeed(desiredSpeed);
        } else {
            return clampSpeed(desiredSpeed);
        }
    }

    private DriveSpeed scaleSpeed(DriveSpeed speed){
        double left = speed.getLeftSpeed() * mMaxSpeed;
        double right = speed.getRightSpeed() * mMaxSpeed;
        return new DriveSpeed(left, right);
    }

    private DriveSpeed clampSpeed(DriveSpeed speed){
        double left = GMath.clamp(speed.getLeftSpeed(), -mMaxSpeed, mMaxSpeed);
        double right = GMath.clamp(speed.getRightSpeed(), -mMaxSpeed, mMaxSpeed);
        return new DriveSpeed(left, right);
    }

    public void setMaxSpeed(double maxSpeed){
        if (maxSpeed < 0) throw new IllegalArgumentException("Max speed must be between 0 and 1");
        mMaxSpeed = maxSpeed;
    }

    public void setShouldScaleSpeeds(boolean shouldScaleSpeeds) {
        this.shouldScaleSpeeds = shouldScaleSpeeds;
    }

    @Override
    public String getName() {
        return NAME;
    }

}