package com.thegongoliers.output.motors;

import com.thegongoliers.annotations.Untested;
import com.thegongoliers.input.voltage.BatteryVoltageSensor;
import com.thegongoliers.input.voltage.VoltageSensor;

@Untested
public class VoltageControlMotorModule implements MotorModule {

    private final double mMaxVoltage;
    private final VoltageSensor mVoltageSensor;

    public VoltageControlMotorModule(double maxVoltage, VoltageSensor voltageSensor) {
        mMaxVoltage = maxVoltage;
        mVoltageSensor = voltageSensor;
    }

    public VoltageControlMotorModule(double maxVoltage) {
        this(maxVoltage, new BatteryVoltageSensor());
    }

    @Override
    public Double run(Double currentSpeed, Double desiredSpeed, double deltaTime) {
        return (desiredSpeed * mMaxVoltage) / mVoltageSensor.getVoltage();
    }
}
