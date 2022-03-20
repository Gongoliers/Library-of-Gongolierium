package com.thegongoliers.output.motors;

import com.thegongoliers.annotations.Untested;
import com.thegongoliers.input.odometry.DistanceSensor;
import com.thegongoliers.output.control.MotionController;
import com.thegongoliers.output.control.PIDController;

@Untested
public class AnchorMotorModule implements MotorModule {

    private boolean mIsEnabled;
    private final DistanceSensor mDistanceSensor;
    private final MotionController mController;
    private double mLastDistance;

    public AnchorMotorModule(DistanceSensor distanceSensor, MotionController controller) {
        mDistanceSensor = distanceSensor;
        mController = controller;
        mIsEnabled = false;
    }

    public AnchorMotorModule(DistanceSensor distanceSensor, double strength) {
        this(distanceSensor, new PIDController(strength, 0, 0));
    }

    @Override
    public double run(double currentSpeed, double desiredSpeed, double deltaTime) {
        if (isAnchoring()) {
            mController.setSetpoint(mLastDistance);
            return mController.calculate(mDistanceSensor.getDistance(), deltaTime);
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
