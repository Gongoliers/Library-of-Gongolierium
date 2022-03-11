package com.thegongoliers.output.motors;

import com.kylecorry.pid.PID;
import com.thegongoliers.annotations.Untested;
import com.thegongoliers.input.odometry.DistanceSensor;

@Untested
public class AnchorMotorModule implements MotorModule {

    private boolean mIsEnabled;
    private final DistanceSensor mDistanceSensor;
    private final PID mPID;
    private double mLastDistance;

    public AnchorMotorModule(DistanceSensor distanceSensor, PID pid) {
        mDistanceSensor = distanceSensor;
        mPID = pid;
        mIsEnabled = false;
    }

    public AnchorMotorModule(DistanceSensor distanceSensor, double strength) {
        this(distanceSensor, new PID(strength, 0, 0));
    }

    @Override
    public double run(double currentSpeed, double desiredSpeed, double deltaTime) {
        if (isAnchoring()) {
            return mPID.calculate(mDistanceSensor.getDistance(), mLastDistance);
        }
        updateLastPosition();
        return desiredSpeed;
    }

    @Override
    public boolean overridesUser() {
        return isAnchoring();
    }

    public void holdPosition() {
        mIsEnabled = true;
    }

    public void stopHoldingPosition() {
        mIsEnabled = false;
    }

    private boolean isAnchoring() {
        return mIsEnabled;
    }

    private void updateLastPosition() {
        mLastDistance = mDistanceSensor.getDistance();
    }
}
