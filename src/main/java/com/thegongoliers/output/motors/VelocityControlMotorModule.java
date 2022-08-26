package com.thegongoliers.output.motors;

import com.thegongoliers.annotations.Untested;
import com.thegongoliers.input.odometry.VelocitySensor;
import com.thegongoliers.output.control.MotionController;
import com.thegongoliers.output.control.PIDController;

@Untested
public class VelocityControlMotorModule implements MotorModule {

    private final VelocitySensor mVelocitySensor;
    private double mMaxVelocity;
    private final MotionController mVelocityController;

    public VelocityControlMotorModule(VelocitySensor velocitySensor, double maxVelocity, MotionController velocityController) {
        mVelocitySensor = velocitySensor;
        mMaxVelocity = maxVelocity;
        mVelocityController = velocityController;
    }

    public VelocityControlMotorModule(VelocitySensor velocitySensor, double maxVelocity, double strength) {
        this(velocitySensor, maxVelocity, new PIDController(strength, 0.0, 0.0));
    }

    @Override
    public Double run(Double currentSpeed, Double desiredSpeed, double deltaTime) {
        var desiredVelocity = desiredSpeed * mMaxVelocity;
        var actualVelocity = mVelocitySensor.getVelocity();
        mVelocityController.setSetpoint(desiredVelocity);
        return mVelocityController.calculate(actualVelocity, deltaTime) + currentSpeed;
    }

    public void setMaxVelocity(double velocity){
        mMaxVelocity = velocity;
    }
}
