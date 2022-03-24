package com.thegongoliers.output.motors;

import com.thegongoliers.annotations.Untested;
import com.thegongoliers.input.odometry.DistanceSensor;
import com.thegongoliers.output.control.MotionController;
import com.thegongoliers.output.control.PIDController;
import com.thegongoliers.utils.Resettable;

@Untested
public class DistanceMotorModule implements MotorModule, Resettable {

    private boolean mIsEnabled;
    private final DistanceSensor mDistanceSensor;
    private final MotionController mController;
    private double mSetpoint;
    private double mDistanceZero;

    public DistanceMotorModule(DistanceSensor distanceSensor, MotionController controller) {
        mDistanceSensor = distanceSensor;
        mController = controller;
        mIsEnabled = false;
    }

    public DistanceMotorModule(DistanceSensor distanceSensor, double strength) {
        this(distanceSensor, new PIDController(strength, 0, 0));
    }

    @Override
    public double run(double currentSpeed, double desiredSpeed, double deltaTime) {
        if (!isRunning()) {
            return desiredSpeed;
        }

        if (mController.atSetpoint()) {
            mIsEnabled = false;
            return desiredSpeed;
        }

        mController.setSetpoint(mSetpoint);
        return mController.calculate(getDistance(), deltaTime);
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
        mController.reset();
        mIsEnabled = true;
    }

    public boolean isRunning() {
        return mIsEnabled;
    }

    private double getDistance() {
        return mDistanceSensor.getDistance() - mDistanceZero;
    }

    @Override
    public void reset() {
        mController.reset();
        mIsEnabled = false;
    }
}
