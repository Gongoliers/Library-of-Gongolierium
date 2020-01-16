package com.thegongoliers.output.drivetrain;

import edu.wpi.first.wpilibj.Encoder;

/**
 * A drivetrain module which will set the wheel velocity rather than percent power
 */
public class VelocityControlModule implements DriveModule {

    private Encoder mLeftEncoder, mRightEncoder;
    private double mStrength;
    private double maxVelocity;

    /**
     * Default constructor
     * @param leftEncoder the left encoder
     * @param rightEncoder the right encoder
     * @param maxVelocity the maximum velocity of the robot in the same units as the encoder's rate (something / second)
     * @param strength the fortify strength (higher values may become unstable, small values recommended. Values must be greater than or equal to 0)
     */
    public VelocityControlModule(Encoder leftEncoder, Encoder rightEncoder, double maxVelocity, double strength){
        super();
        setLeftEncoder(leftEncoder);
        setRightEncoder(rightEncoder);
        setStrength(strength);
        setMaxVelocity(maxVelocity);
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
        double desiredLeftSpeed = getVelocity(desiredSpeed.getLeftSpeed());
        double desiredRightSpeed = getVelocity(desiredSpeed.getRightSpeed());
        
        double left = getAdjustedSpeed(currentSpeed.getLeftSpeed(), mLeftEncoder.getRate(), desiredLeftSpeed);
        double right = getAdjustedSpeed(currentSpeed.getRightSpeed(), mRightEncoder.getRate(), desiredRightSpeed);

        return new DriveSpeed(left, right);
    }

    private double getVelocity(double pwm){
        return pwm * maxVelocity;
    }

    private double getAdjustedSpeed(double lastSpeed, double actualVelocity, double desiredVelocity){        
        double error = desiredVelocity - actualVelocity;
        return mStrength * error + lastSpeed;
    }

    public void setLeftEncoder(Encoder leftEncoder) {
        if (leftEncoder == null) throw new IllegalArgumentException("Left encoder must be non-null");
        mLeftEncoder = leftEncoder;
    }

    public void setRightEncoder(Encoder rightEncoder) {
        if (rightEncoder == null) throw new IllegalArgumentException("Right encoder must be non-null");
        mRightEncoder = rightEncoder;
    }

    public void setStrength(double strength) {
        if (strength < 0) throw new IllegalArgumentException("Strength must be non-negative");
        mStrength = strength;
    }

    public void setMaxVelocity(double maxVelocity) {
        if (maxVelocity <= 0) throw new IllegalArgumentException("Max velocity must be positive");
        this.maxVelocity = maxVelocity;
    }
}