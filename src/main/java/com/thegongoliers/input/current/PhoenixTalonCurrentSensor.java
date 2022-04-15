package com.thegongoliers.input.current;

import com.ctre.phoenix.motorcontrol.can.BaseTalon;

public class PhoenixTalonCurrentSensor implements CurrentSensor {

    private final BaseTalon mMotor;
    private final boolean mUseInputCurrent;

    public PhoenixTalonCurrentSensor(BaseTalon motor, boolean useInputCurrent) {
        mMotor = motor;
        mUseInputCurrent = useInputCurrent;
    }

    @Override
    public double getCurrent() {
        if (mUseInputCurrent) {
            return mMotor.getSupplyCurrent();
        } else {
            return mMotor.getStatorCurrent();
        }
    }
}
