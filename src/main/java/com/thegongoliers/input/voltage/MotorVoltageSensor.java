package com.thegongoliers.input.voltage;

import com.thegongoliers.input.PDP;
import edu.wpi.first.wpilibj.SpeedController;

public class MotorVoltageSensor implements VoltageSensor {

    private final SpeedController motor;

    public MotorVoltageSensor(SpeedController motor) {
        this.motor = motor;

    }

    @Override
    public double getVoltage() {
        return Math.abs(motor.get()) * PDP.getInstance().getBatteryVoltage();
    }
}
