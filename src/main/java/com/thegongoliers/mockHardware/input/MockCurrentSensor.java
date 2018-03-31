package com.thegongoliers.mockHardware.input;

import com.thegongoliers.input.current.CurrentSensor;

public class MockCurrentSensor implements CurrentSensor {

    private double current = 0;

    public void setCurrent(double current) {
        this.current = current;
    }

    @Override
    public double getCurrent() {
        return current;
    }
}
