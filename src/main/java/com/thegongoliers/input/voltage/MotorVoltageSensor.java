package com.thegongoliers.input.voltage;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class MotorVoltageSensor implements VoltageSensor {

    private final MotorController motor;
    private final VoltageSensor batteryVoltage;

    public MotorVoltageSensor(MotorController motor) {
        this.motor = motor;
        batteryVoltage = new BatteryVoltageSensor();

    }

    @Override
    public double getVoltage() {
        return Math.abs(motor.get()) * batteryVoltage.getVoltage();
    }
}
