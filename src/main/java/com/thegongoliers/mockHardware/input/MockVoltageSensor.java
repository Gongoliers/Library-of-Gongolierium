package com.thegongoliers.mockHardware.input;

import com.thegongoliers.input.voltage.VoltageSensor;

public class MockVoltageSensor implements VoltageSensor {

    private double voltage = 0;

    public void setVoltage(double voltage){
        this.voltage = voltage;
    }

    @Override
    public double getVoltage() {
        return voltage;
    }
}
