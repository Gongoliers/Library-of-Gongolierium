package com.thegongoliers.mockHardware.input;

import com.thegongoliers.input.odometry.IEncoder;

public class MockEncoder implements IEncoder {

    private double pulses = 0;
    private double distancePerPulse = 1;
    private double velocity = 0;
    private boolean inverted = false;

    @Override
    public double getPulses() {
        return pulses;
    }

    @Override
    public double getDistance() {
        return pulses * distancePerPulse;
    }

    @Override
    public double getVelocity() {
        return velocity;
    }

    @Override
    public void setDistancePerPulse(double distancePerPulse) {
        this.distancePerPulse = distancePerPulse;
    }

    @Override
    public void reset() {
        pulses = 0;
        velocity = 0;
    }

    @Override
    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

    @Override
    public boolean isInverted() {
        return inverted;
    }

    public void setPulses(double pulses) {
        this.pulses = pulses;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }
}
