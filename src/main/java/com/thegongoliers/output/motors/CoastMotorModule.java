package com.thegongoliers.output.motors;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

public class CoastMotorModule implements MotorModule {

    private BaseMotorController mMotor;

    public CoastMotorModule(BaseMotorController motor) {
        mMotor = motor;
    }

    @Override
    public Double run(Double currentSpeed, Double desiredSpeed, double deltaTime) {
        mMotor.setNeutralMode(NeutralMode.Coast);
        return desiredSpeed;
    }
}
