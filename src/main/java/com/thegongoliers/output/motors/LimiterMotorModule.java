package com.thegongoliers.output.motors;

import com.thegongoliers.annotations.Untested;

import java.util.function.BooleanSupplier;

@Untested
public class LimiterMotorModule implements MotorModule {

    private BooleanSupplier mPositiveLimit;
    private BooleanSupplier mNegativeLimit;

    public LimiterMotorModule(BooleanSupplier positiveLimit, BooleanSupplier negativeLimit) {
        mPositiveLimit = positiveLimit;
        mNegativeLimit = negativeLimit;

        if (mPositiveLimit == null) {
            mPositiveLimit = () -> false;
        }

        if (mNegativeLimit == null) {
            mNegativeLimit = () -> false;
        }
    }

    public void setNegativeLimit(BooleanSupplier mNegativeLimit) {
        this.mNegativeLimit = mNegativeLimit;
    }

    public void setPositiveLimit(BooleanSupplier mPositiveLimit) {
        this.mPositiveLimit = mPositiveLimit;
    }

    @Override
    public Double run(Double currentSpeed, Double desiredSpeed, double deltaTime) {
        if (shouldStop(desiredSpeed)) {
            return 0.0;
        }
        return desiredSpeed;
    }

    public boolean isAtPositiveLimit(){
        return mPositiveLimit.getAsBoolean();
    }

    public boolean isAtNegativeLimit(){
        return mNegativeLimit.getAsBoolean();
    }

    private boolean shouldStop(double speed) {
        if (speed > 0 && isAtPositiveLimit()) {
            return true;
        }

        if (speed < 0 && isAtNegativeLimit()) {
            return true;
        }

        return false;
    }
}
