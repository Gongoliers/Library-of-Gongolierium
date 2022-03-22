package com.thegongoliers.output.motors;

import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.time.RobotClock;

public class PulseMotorModule implements MotorModule {

    private final double mOnDuration;
    private final double mOffDuration;
    private final Clock mClock;

    private double mLastStateChangeTime;

    private final int STATE_PULSE_ON = 1;
    private final int STATE_PULSE_OFF = 2;

    private int mState = STATE_PULSE_ON;

    public PulseMotorModule(double onDuration, double offDuration, Clock clock){
        mOnDuration = onDuration;
        mOffDuration = offDuration;
        mClock = clock;
    }

    public PulseMotorModule(double onDuration, double offDuration){
        this(onDuration, offDuration, new RobotClock());
    }

    @Override
    public double run(double currentSpeed, double desiredSpeed, double deltaTime) {
        if (desiredSpeed == 0.0){
            changeState(STATE_PULSE_ON);
            return 0;
        }

        switch (mState){
            case STATE_PULSE_ON:
                if (mClock.getTime() - mLastStateChangeTime >= mOnDuration){
                    changeState(STATE_PULSE_OFF);
                    return 0;
                }
                return desiredSpeed;
            case STATE_PULSE_OFF:
                if (mClock.getTime() - mLastStateChangeTime >= mOffDuration){
                    changeState(STATE_PULSE_ON);
                    return desiredSpeed;
                }
                return 0;
        }

        return 0;
    }

    private void changeState(int state){
        mState = state;
        mLastStateChangeTime = mClock.getTime();
    }

}
