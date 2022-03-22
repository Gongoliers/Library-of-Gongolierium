package com.thegongoliers.output.motors;

public class InvertMotorModule implements MotorModule {

    private boolean mIsEnabled;

    public InvertMotorModule() {
        this(false);
    }

    public InvertMotorModule(boolean enabled) {
        mIsEnabled = enabled;
    }

    public void setEnabled(boolean enabled) {
        mIsEnabled = enabled;
    }

    public boolean isEnabled() {
        return mIsEnabled;
    }

    @Override
    public double run(double currentSpeed, double desiredSpeed, double deltaTime) {
        if (!mIsEnabled) return desiredSpeed;
        return -desiredSpeed;
    }
}
