package com.thegongoliers.output.drivetrain.swerve;

import com.thegongoliers.math.GMath;
import com.thegongoliers.utils.IModule;

/**
 * A drivetrain module which will constrain the maximum speed of the robot. 
 */
public class SpeedConstraintSwerveModule implements IModule<SwerveSpeed> {

    private double mMaxSpeed;
    private boolean shouldScaleSpeeds;

    /**
     * Default constructor
     * @param maxSpeed The maximum percent speed
     * @param shouldScaleSpeeds True if the speeds should be scaled to the clamped range, or false otherwise
     */
    public SpeedConstraintSwerveModule(double maxSpeed, boolean shouldScaleSpeeds){
        super();
        setMaxSpeed(maxSpeed);
        setShouldScaleSpeeds(shouldScaleSpeeds);
    }

    @Override
    public SwerveSpeed run(SwerveSpeed currentSpeed, SwerveSpeed desiredSpeed, double deltaTime) {
        if (shouldScaleSpeeds){
            return scaleSpeed(desiredSpeed);
        } else {
            return clampSpeed(desiredSpeed);
        }
    }

    private SwerveSpeed scaleSpeed(SwerveSpeed speed){
        double x = speed.getX() * mMaxSpeed;
        double y = speed.getY() * mMaxSpeed;
        double rotation = speed.getRotation() * mMaxSpeed;
        return new SwerveSpeed(x, y, rotation);
    }

    private SwerveSpeed clampSpeed(SwerveSpeed speed){
        double x = GMath.clamp(speed.getX(), -mMaxSpeed, mMaxSpeed);
        double y = GMath.clamp(speed.getY(), -mMaxSpeed, mMaxSpeed);
        double rotation = GMath.clamp(speed.getRotation(), -mMaxSpeed, mMaxSpeed);
        return new SwerveSpeed(x, y, rotation);
    }

    public void setMaxSpeed(double maxSpeed){
        if (maxSpeed < 0) throw new IllegalArgumentException("Max speed must be between 0 and 1");
        mMaxSpeed = maxSpeed;
    }

    public void setShouldScaleSpeeds(boolean shouldScaleSpeeds) {
        this.shouldScaleSpeeds = shouldScaleSpeeds;
    }
}