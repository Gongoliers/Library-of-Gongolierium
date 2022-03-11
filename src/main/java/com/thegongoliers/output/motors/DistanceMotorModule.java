package com.thegongoliers.output.motors;

import com.kylecorry.pid.PID;
import com.thegongoliers.annotations.Untested;
import com.thegongoliers.input.odometry.DistanceSensor;

@Untested
public class DistanceMotorModule implements MotorModule {

    private boolean mIsEnabled;
    private final DistanceSensor mDistanceSensor;
    private final PID mPID;
    private double mSetpoint;
    private double mDistanceZero;

    public DistanceMotorModule(DistanceSensor distanceSensor, PID pid) {
        mDistanceSensor = distanceSensor;
        mPID = pid;
        mIsEnabled = false;
    }

    public DistanceMotorModule(DistanceSensor distanceSensor, double strength) {
        this(distanceSensor, new PID(strength, 0, 0));
    }

    @Override
    public double run(double currentSpeed, double desiredSpeed, double deltaTime) {
        if (!isRunning()) {
            return desiredSpeed;
        }

        if (mPID.atSetpoint()) {
            mIsEnabled = false;
            return desiredSpeed;
        }

        return mPID.calculate(getDistance(), mSetpoint, deltaTime);
    }

    @Override
    public boolean overridesUser() {
        return isRunning();
    }

    /**
     * Move by a provided distance
     *
     * @param distance The distance to move to
     */
    public void setSetpoint(double distance) {
        mSetpoint = distance;
        mDistanceZero = mDistanceSensor.getDistance();
        mPID.reset();
        mIsEnabled = true;
    }

    public boolean isRunning() {
        return mIsEnabled;
    }

    private double getDistance() {
        return mDistanceSensor.getDistance() - mDistanceZero;
    }
}
