package com.thegongoliers.input.voltage;

import edu.wpi.first.wpilibj.SpeedController;

public class MotorVoltageSensor implements VoltageSensor {

    private final SpeedController motor;
    private final VoltageSensor batteryVoltage;

    public MotorVoltageSensor(SpeedController motor) {
        this.motor = motor;
        batteryVoltage = new BatteryVoltageSensor();

    }

    @Override
    public double getVoltage() {
        return Math.abs(motor.get()) * batteryVoltage.getVoltage();
    }
}
