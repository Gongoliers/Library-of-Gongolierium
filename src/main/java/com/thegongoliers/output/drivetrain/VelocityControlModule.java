package com.thegongoliers.output.drivetrain;

import com.thegongoliers.input.odometry.VelocitySensor;
import com.thegongoliers.output.control.MotionController;
import com.thegongoliers.output.control.PIDController;

/**
 * A drivetrain module which will set the wheel velocity rather than percent power
 */
public class VelocityControlModule implements DriveModule {

    private VelocitySensor mLeftEncoder, mRightEncoder;
    private MotionController mController;
    private double maxVelocity;

    /**
     * Default constructor
     *
     * @param leftEncoder  the left encoder
     * @param rightEncoder the right encoder
     * @param maxVelocity  the maximum velocity of the robot in the same units as the encoder's rate (something / second)
     * @param strength     the velocity correction strength
     */
    public VelocityControlModule(VelocitySensor leftEncoder, VelocitySensor rightEncoder, double maxVelocity, double strength) {
        this(leftEncoder, rightEncoder, maxVelocity, new PIDController(strength, 0, 0));
    }

    /**
     * Default constructor
     *
     * @param leftEncoder        the left encoder
     * @param rightEncoder       the right encoder
     * @param maxVelocity        the maximum velocity of the robot in the same units as the encoder's rate (something / second)
     * @param velocityController the PID to control velocity
     */
    public VelocityControlModule(VelocitySensor leftEncoder, VelocitySensor rightEncoder, double maxVelocity, MotionController velocityController) {
        super();
        setLeftEncoder(leftEncoder);
        setRightEncoder(rightEncoder);
        setController(velocityController);
        setMaxVelocity(maxVelocity);
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
        double desiredLeftSpeed = getVelocity(desiredSpeed.getLeftSpeed());
        double desiredRightSpeed = getVelocity(desiredSpeed.getRightSpeed());

        double left = getAdjustedSpeed(currentSpeed.getLeftSpeed(), mLeftEncoder.getVelocity(), desiredLeftSpeed, deltaTime);
        double right = getAdjustedSpeed(currentSpeed.getRightSpeed(), mRightEncoder.getVelocity(), desiredRightSpeed, deltaTime);

        return new DriveSpeed(left, right);
    }

    private double getVelocity(double pwm) {
        return pwm * maxVelocity;
    }

    private double getAdjustedSpeed(double lastSpeed, double actualVelocity, double desiredVelocity, double delta) {
        mController.setSetpoint(desiredVelocity);
        return mController.calculate(actualVelocity, delta) + lastSpeed;
    }

    public void setLeftEncoder(VelocitySensor leftEncoder) {
        if (leftEncoder == null) throw new IllegalArgumentException("Left encoder must be non-null");
        mLeftEncoder = leftEncoder;
    }

    public void setRightEncoder(VelocitySensor rightEncoder) {
        if (rightEncoder == null) throw new IllegalArgumentException("Right encoder must be non-null");
        mRightEncoder = rightEncoder;
    }

    public void setController(MotionController controller) {
        if (controller == null) throw new IllegalArgumentException("Controller must be non-null");
        mController = controller;
    }

    public void setMaxVelocity(double maxVelocity) {
        if (maxVelocity <= 0) throw new IllegalArgumentException("Max velocity must be positive");
        this.maxVelocity = maxVelocity;
    }
}