package com.thegongoliers.output.motors;

import com.thegongoliers.output.gears.Ratchet;
import com.thegongoliers.output.gears.RatchetState;

public class RatchetMotorModule implements MotorModule {

    private final Ratchet mRatchet;
    private final RatchetState mDefaultState;

    public RatchetMotorModule(Ratchet ratchet, RatchetState defaultState) {
        mRatchet = ratchet;
        mDefaultState = defaultState;
    }

    public RatchetMotorModule(Ratchet ratchet) {
        this(ratchet, null);
    }

    @Override
    public Double run(Double currentSpeed, Double desiredSpeed, double deltaTime) {

        if (desiredSpeed == 0.0 && mDefaultState != null) {
            mRatchet.set(mDefaultState);
        }

        if (mRatchet.isAllowed(desiredSpeed)) {
            return desiredSpeed;
        }

        if (desiredSpeed > 0) {
            mRatchet.set(RatchetState.LockReverse);
        }

        if (desiredSpeed < 0) {
            mRatchet.set(RatchetState.LockForward);
        }

        return 0.0;
    }
}
