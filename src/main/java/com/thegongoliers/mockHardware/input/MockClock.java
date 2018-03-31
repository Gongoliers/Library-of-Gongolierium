package com.thegongoliers.mockHardware.input;

import com.thegongoliers.input.time.Clock;

public class MockClock implements Clock {

    private double time = 0;

    @Override
    public double getTime() {
        return time;
    }

    public void setTime(double time){
        this.time = time;
    }
}
