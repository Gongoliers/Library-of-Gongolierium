package com.thegongoliers.mockHardware.input;

import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class MockPotentiometer implements Potentiometer {

    private PIDSourceType pidSource;
    private double angle;

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
        this.pidSource = pidSource;
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return pidSource;
    }

    @Override
    public double pidGet() {
        return get();
    }

    @Override
    public double get() {
        return angle;
    }
    
    public void setAngle(double angle){
        this.angle = angle;
    }

}