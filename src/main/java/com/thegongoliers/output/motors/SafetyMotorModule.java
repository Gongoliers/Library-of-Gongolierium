package com.thegongoliers.output.motors;

import java.util.function.BooleanSupplier;

public class SafetyMotorModule implements MotorModule {

    private final BooleanSupplier mCanRun;

    public SafetyMotorModule(BooleanSupplier canRun){
        mCanRun = canRun;
    }

    @Override
    public Double run(Double currentSpeed, Double desiredSpeed, double deltaTime) {
        if (mCanRun.getAsBoolean()){
            return desiredSpeed;
        }

        return 0.0;
    }
}
