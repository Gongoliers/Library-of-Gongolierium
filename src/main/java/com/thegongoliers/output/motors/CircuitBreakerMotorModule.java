package com.thegongoliers.output.motors;

import com.thegongoliers.input.switches.Switch;
import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.time.RobotClock;
import com.thegongoliers.utils.Resettable;

public class CircuitBreakerMotorModule implements MotorModule {

    private final Switch mSwitch;
    private final double mBreakDuration;
    private final Clock mClock;

    private double mLastTripTime;

    private boolean mIsTripped;

    public CircuitBreakerMotorModule(Switch tripSwitch, double breakDuration, Clock clock){
        mSwitch = tripSwitch;
        mBreakDuration = breakDuration;
        mClock = clock;
    }

    public CircuitBreakerMotorModule(Switch tripSwitch, double breakDuration){
        this(tripSwitch, breakDuration, new RobotClock());
    }

    @Override
    public double run(double currentSpeed, double desiredSpeed, double deltaTime) {
        if (mSwitch.isTriggered()){
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
