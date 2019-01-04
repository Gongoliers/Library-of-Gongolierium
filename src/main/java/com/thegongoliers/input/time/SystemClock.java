package com.thegongoliers.input.time;

public class SystemClock implements Clock {
    @Override
    public double getTime() {
        return System.currentTimeMillis() / 1000.0;
    }
}
