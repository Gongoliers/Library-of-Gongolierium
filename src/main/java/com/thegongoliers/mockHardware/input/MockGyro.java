package com.thegongoliers.mockHardware.input;

import edu.wpi.first.wpilibj.interfaces.Gyro;

public class MockGyro implements Gyro {

    private double angle;
    private double rate;

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    @Override
    public void calibrate() {

    }

    @Override
    public void reset() {
        this.angle = 0;
    }

    @Override
    public double getAngle() {
        return angle;
    }

    @Override
    public double getRate() {
        return rate;
    }

    @Override
    public void close() {
    }
}
