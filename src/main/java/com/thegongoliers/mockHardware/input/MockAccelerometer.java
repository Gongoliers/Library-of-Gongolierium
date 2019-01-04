package com.thegongoliers.mockHardware.input;

import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public class MockAccelerometer implements Accelerometer {

    private double x = 0;
    private double y = 0;
    private double z = 0;

    @Override
    public void setRange(Range range) {
        // Stub
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getZ() {
        return z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
