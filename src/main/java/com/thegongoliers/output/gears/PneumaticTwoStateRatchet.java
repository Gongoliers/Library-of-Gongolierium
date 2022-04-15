package com.thegongoliers.output.gears;

import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.time.RobotClock;
import com.thegongoliers.output.interfaces.Piston;

public class PneumaticTwoStateRatchet implements Ratchet {

    private final Piston mPiston;
    private final RatchetState mExtendState;
    private final RatchetState mRetractState;
    private final double mEngageTimeSeconds;
    private final Clock mClock;
    private double mLastTime = 0.0;

    public PneumaticTwoStateRatchet(Piston piston, RatchetState extendState, RatchetState retractState, double engageTimeSeconds, Clock clock) {
        mPiston = piston;
        mExtendState = extendState;
        mRetractState = retractState;
        mEngageTimeSeconds = engageTimeSeconds;
        mClock = clock;
    }

    public PneumaticTwoStateRatchet(Piston piston, RatchetState extendState, RatchetState retractState, double engageTimeSeconds) {
        this(piston, extendState, retractState, engageTimeSeconds, new RobotClock());
    }

    public PneumaticTwoStateRatchet(Piston piston, RatchetState extendState, RatchetState retractState) {
        this(piston, extendState, retractState, 0.0, new RobotClock());
    }

    @Override
    public RatchetState get() {
        if (mPiston.isExtended()) {
            return mExtendState;
        } else {
            return mRetractState;
        }
    }

    @Override
    public boolean isSet() {
        return mClock.getTime() - mLastTime >= mEngageTimeSeconds;
    }

    @Override
    public void set(RatchetState state) {
        if (get() == state) {
            return;
        }

        if (state == mExtendState) {
            mPiston.extend();
            mLastTime = mClock.getTime();
        } else if (state == mRetractState) {
            mPiston.retract();
            mLastTime = mClock.getTime();
        } else if (mRetractState == RatchetState.Neutral) {
            // If no valid state is found, put the ratchet in neutral to allow motion
            mPiston.retract();
            mLastTime = mClock.getTime();
        } else if (mExtendState == RatchetState.Neutral) {
            // If no valid state is found, put the ratchet in neutral to allow motion
            mPiston.extend();
            mLastTime = mClock.getTime();
        }
    }
}
