package com.thegongoliers.output.motors;

import com.thegongoliers.annotations.Untested;

import java.util.function.BooleanSupplier;

@Untested
public class LimitedMotorModule implements MotorModule {

    private BooleanSupplier mPositiveLimit;
    private BooleanSupplier mNegativeLimit;

    public LimitedMotorModule(BooleanSupplier positiveLimit, BooleanSupplier negativeLimit) {
        mPositiveLimit = positiveLimit;
        mNegativeLimit = negativeLimit;

        if (mPositiveLimit == null) {
            mPositiveLimit = () -> false;
        }

        if (mNegativeLimit == null) {
            mNegativeLimit = () -> false;
        }
    }

    @Override
    public double run(double currentSpeed, double desiredSpeed, double deltaTime) {
        if (shouldStop(desiredSpeed)) {
            return 0.0;
        }
        return desiredSpeed;
    }

    private boolean shouldStop(double speed) {
        if (speed > 0 && mPositiveLimit.getAsBoolean()) {
            return true;
        }

        if (speed < 0 && mNegativeLimit.getAsBoolean()) {
            return true;
        }

        return false;
    }
}
