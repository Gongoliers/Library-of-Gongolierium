package com.thegongoliers.output.motors;

import com.thegongoliers.annotations.Untested;
import com.thegongoliers.input.odometry.DistanceSensor;
import com.thegongoliers.output.control.MotionController;
import com.thegongoliers.output.control.PIDController;
import com.thegongoliers.utils.Resettable;

@Untested
public class AnchorMotorModule implements MotorModule, Resettable {

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
    public Double run(Double currentSpeed, Double desiredSpeed, double deltaTime) {
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

    @Override
    public void reset() {
        stopHoldingPosition();
    }
}
