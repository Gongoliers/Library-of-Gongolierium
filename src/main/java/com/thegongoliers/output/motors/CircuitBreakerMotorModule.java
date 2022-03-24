package com.thegongoliers.output.motors;

import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.time.RobotClock;
import com.thegongoliers.utils.Resettable;

import java.util.function.BooleanSupplier;

public class CircuitBreakerMotorModule implements MotorModule {

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
    public double run(double currentSpeed, double desiredSpeed, double deltaTime) {
        if (mSwitch.getAsBoolean()){
            trip();
            if (mSwitch instanceof Resettable){
                ((Resettable) mSwitch).reset();
            }
        }

        if (mIsTripped && mClock.getTime() - mLastTripTime < mBreakDuration){
            mIsTripped = false;
        }

        if (mIsTripped){
            return 0;
        }

        return desiredSpeed;
    }

    private void trip(){
        mIsTripped = true;
        mLastTripTime = mClock.getTime();
    }

}
