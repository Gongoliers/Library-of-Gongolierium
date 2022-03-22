package com.thegongoliers.output.motors;

import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.time.RobotClock;

public class DelayMotorModule implements MotorModule {

    private final Clock mClock;
    private final double mDelay;

    private double lastStopTime;

    public DelayMotorModule(double delay, Clock clock){
        mDelay = delay;
        mClock = clock;
    }

    public DelayMotorModule(double delay){
        this(delay, new RobotClock());
    }

    @Override
    public double run(double currentSpeed, double desiredSpeed, double deltaTime) {
        if (desiredSpeed == 0.0){
            lastStopTime = mClock.getTime();
        }

        if (mClock.getTime() - lastStopTime >= mDelay){
            return desiredSpeed;
        }

        return 0;
    }
}
