package com.thegongoliers.output.motors;

import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.time.RobotClock;
import com.thegongoliers.utils.Resettable;

import java.util.function.BooleanSupplier;

public class CircuitBreakerMotorModule implements MotorModule, Resettable {

    private final BooleanSupplier mSwitch;
    private final double mBreakDuration;
    private final Clock mClock;

    private double mLastTripTime;

    private boolean mIsTripped;

    public CircuitBreakerMotorModule(BooleanSupplier tripSwitch, double breakDuration, Clock clock){
        mSwitch = tripSwitch;
        mBreakDuration = breakDuration;
        mClock = clock;
    }

    public CircuitBreakerMotorModule(BooleanSupplier tripSwitch, double breakDuration){
        this(tripSwitch, breakDuration, new RobotClock());
    }

    @Override
    public Double run(Double currentSpeed, Double desiredSpeed, double deltaTime) {
        if (mSwitch.getAsBoolean()){
            trip();
            if (mSwitch instanceof Resettable){
                ((Resettable) mSwitch).reset();
            }
        }

        if (mIsTripped && mClock.getTime() - mLastTripTime < mBreakDuration){
            reset();
        }

        if (mIsTripped){
            return 0.0;
        }

        return desiredSpeed;
    }

    private void trip(){
        mIsTripped = true;
        mLastTripTime = mClock.getTime();
    }

    @Override
    public void reset() {
        mIsTripped = false;
    }
}
