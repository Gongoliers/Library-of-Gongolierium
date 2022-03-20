package com.thegongoliers.output.control;

public class BangBangController implements MotionController {

    private double mSetpoint = 0.0;
    private double mTolerance;
    private final double mSpeed;
    private double mError;

    public BangBangController(double speed, double tolerance) {
        mSpeed = speed;
        mTolerance = tolerance;
    }

    public BangBangController(double speed) {
        this(speed, 0.0);
    }

    public void setTolerance(double tolerance) {
        mTolerance = tolerance;
    }

    @Override
    public void setSetpoint(double setpoint) {
        mSetpoint = setpoint;
    }

    @Override
    public double calculate(double actual, double deltaTime) {
        mError = mSetpoint - actual;
        if (atSetpoint()) {
            return 0.0;
        }

        if (actual < mSetpoint) {
            return mSpeed;
        }

        return -mSpeed;
    }

    @Override
    public boolean atSetpoint() {
        return Math.abs(mError) <= mTolerance;
    }

    @Override
    public MotionController copy() {
        return new BangBangController(mSpeed, mTolerance);
    }

    @Override
    public void reset() {
        mError = Double.NaN;
    }
}
