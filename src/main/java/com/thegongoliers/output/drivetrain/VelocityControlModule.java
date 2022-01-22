package com.thegongoliers.output.drivetrain;

import com.kylecorry.pid.PID;
import com.thegongoliers.input.odometry.VelocitySensor;

/**
 * A drivetrain module which will set the wheel velocity rather than percent power
 */
public class VelocityControlModule implements DriveModule {

    private VelocitySensor mLeftEncoder, mRightEncoder;
    private PID mPID;
    private double maxVelocity;

    /**
     * Default constructor
     * @param leftEncoder the left encoder
     * @param rightEncoder the right encoder
     * @param maxVelocity the maximum velocity of the robot in the same units as the encoder's rate (something / second)
     * @param strength the velocity correction strength
     */
    public VelocityControlModule(VelocitySensor leftEncoder, VelocitySensor rightEncoder, double maxVelocity, double strength){
        this(leftEncoder, rightEncoder, maxVelocity, new PID(strength, 0, 0));
    }

    /**
     * Default constructor
     * @param leftEncoder the left encoder
     * @param rightEncoder the right encoder
     * @param maxVelocity the maximum velocity of the robot in the same units as the encoder's rate (something / second)
     * @param velocityPID the PID to control velocity
     */
    public VelocityControlModule(VelocitySensor leftEncoder, VelocitySensor rightEncoder, double maxVelocity, PID velocityPID){
        super();
        setLeftEncoder(leftEncoder);
        setRightEncoder(rightEncoder);
        setPID(velocityPID);
        setMaxVelocity(maxVelocity);
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
        double desiredLeftSpeed = getVelocity(desiredSpeed.getLeftSpeed());
        double desiredRightSpeed = getVelocity(desiredSpeed.getRightSpeed());
        
        double left = getAdjustedSpeed(currentSpeed.getLeftSpeed(), mLeftEncoder.getVelocity(), desiredLeftSpeed);
        double right = getAdjustedSpeed(currentSpeed.getRightSpeed(), mRightEncoder.getVelocity(), desiredRightSpeed);

        return new DriveSpeed(left, right);
    }

    private double getVelocity(double pwm){
        return pwm * maxVelocity;
    }

    private double getAdjustedSpeed(double lastSpeed, double actualVelocity, double desiredVelocity){        
        return mPID.calculate(actualVelocity, desiredVelocity) + lastSpeed;
    }

    public void setLeftEncoder(VelocitySensor leftEncoder) {
        if (leftEncoder == null) throw new IllegalArgumentException("Left encoder must be non-null");
        mLeftEncoder = leftEncoder;
    }

    public void setRightEncoder(VelocitySensor rightEncoder) {
        if (rightEncoder == null) throw new IllegalArgumentException("Right encoder must be non-null");
        mRightEncoder = rightEncoder;
    }

    public void setPID(PID pid) {
        if (pid == null) throw new IllegalArgumentException("PID must be non-null");
        mPID = pid;
    }

    public void setMaxVelocity(double maxVelocity) {
        if (maxVelocity <= 0) throw new IllegalArgumentException("Max velocity must be positive");
        this.maxVelocity = maxVelocity;
    }
}