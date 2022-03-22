package com.thegongoliers.output.motors;

import com.thegongoliers.input.current.HighCurrentSensor;
import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.time.RobotClock;

public class CircuitBreakerMotorModule implements MotorModule {

    private final HighCurrentSensor mCurrentSensor;
    private final double mBreakDuration;
    private final Clock mClock;

    private double mLastTripTime;

    private boolean mIsTripped;

    public CircuitBreakerMotorModule(HighCurrentSensor currentSensor, double breakDuration, Clock clock){
        mCurrentSensor = currentSensor;
        mBreakDuration = breakDuration;
        mClock = clock;
    }

    public CircuitBreakerMotorModule(HighCurrentSensor currentSensor, double breakDuration){
        this(currentSensor, breakDuration, new RobotClock());
    }

    @Override
    public double run(double currentSpeed, double desiredSpeed, double deltaTime) {
        if (mCurrentSensor.isTriggered()){
            trip();
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
