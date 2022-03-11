package com.thegongoliers.output.motors;

import com.kylecorry.pid.PID;
import com.thegongoliers.annotations.Untested;
import com.thegongoliers.input.odometry.VelocitySensor;

@Untested
public class VelocityControlMotorModule implements MotorModule {

    private final VelocitySensor mVelocitySensor;
    private double mMaxVelocity;
    private final PID mVelocityPID;

    public VelocityControlMotorModule(VelocitySensor velocitySensor, double maxVelocity, PID velocityPID) {
        mVelocitySensor = velocitySensor;
        mMaxVelocity = maxVelocity;
        mVelocityPID = velocityPID;
    }

    public VelocityControlMotorModule(VelocitySensor velocitySensor, double maxVelocity, double strength) {
        this(velocitySensor, maxVelocity, new PID(strength, 0.0, 0.0));
    }

    @Override
    public double run(double currentSpeed, double desiredSpeed, double deltaTime) {
        var desiredVelocity = desiredSpeed * mMaxVelocity;
        var actualVelocity = mVelocitySensor.getVelocity();
        return mVelocityPID.calculate(actualVelocity, desiredVelocity) + currentSpeed;
    }

    public void setMaxVelocity(double velocity){
        mMaxVelocity = velocity;
    }
}
