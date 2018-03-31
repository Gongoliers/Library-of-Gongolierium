package com.thegongoliers.output;

import edu.wpi.first.wpilibj.SpeedController;

public class MockSpeedController implements SpeedController {

    private double speed = 0;
    private boolean inverted = false;

    @Override
    public void set(double speed) {
        this.speed = speed * (inverted ? -1 : 1);
    }

    @Override
    public double get() {
        return speed;
    }

    @Override
    public void setInverted(boolean isInverted) {
        this.inverted = isInverted;
    }

    @Override
    public boolean getInverted() {
        return inverted;
    }

    @Override
    public void disable() {
        set(0);
    }

    @Override
    public void stopMotor() {
        set(0);
    }

    @Override
    public void pidWrite(double output) {
        set(output);
    }
}
