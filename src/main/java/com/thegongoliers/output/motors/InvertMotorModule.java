package com.thegongoliers.output.motors;

import java.util.function.BooleanSupplier;

public class InvertMotorModule implements MotorModule {

    private BooleanSupplier mIsEnabled;

    public InvertMotorModule() {
        this(false);
    }

    public InvertMotorModule(boolean enabled) {
        mIsEnabled = () -> enabled;
    }

    public InvertMotorModule(BooleanSupplier isEnabled) {
        mIsEnabled = isEnabled;
    }

    public void setEnabled(boolean enabled) {
        mIsEnabled = () -> enabled;
    }

    public void setEnabled(BooleanSupplier enabled) {
        mIsEnabled = enabled;
    }

    public boolean isEnabled() {
        return mIsEnabled.getAsBoolean();
    }

    @Override
    public Double run(Double currentSpeed, Double desiredSpeed, double deltaTime) {
        if (!isEnabled()) return desiredSpeed;
        return -desiredSpeed;
    }
}
