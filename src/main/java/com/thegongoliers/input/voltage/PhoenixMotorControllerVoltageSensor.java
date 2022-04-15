package com.thegongoliers.input.voltage;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.thegongoliers.input.current.CurrentSensor;

public class PhoenixMotorControllerVoltageSensor implements CurrentSensor {

    private final BaseMotorController mMotor;
    private final boolean mUseInputVoltage;

    public PhoenixMotorControllerVoltageSensor(BaseMotorController motor, boolean useInputVoltage) {
        mMotor = motor;
        mUseInputVoltage = useInputVoltage;
    }

    @Override
    public double getCurrent() {
        if (mUseInputVoltage) {
            return mMotor.getBusVoltage();
        } else {
            return mMotor.getMotorOutputVoltage();
        }
    }
}
