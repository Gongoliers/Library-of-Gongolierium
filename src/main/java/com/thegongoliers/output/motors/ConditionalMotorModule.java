package com.thegongoliers.output.motors;

import java.util.function.BooleanSupplier;

public class ConditionalMotorModule implements MotorModule {

    private final BooleanSupplier mCanRun;

    public ConditionalMotorModule(BooleanSupplier canRun){
        mCanRun = canRun;
    }

    @Override
    public double run(double currentSpeed, double desiredSpeed, double deltaTime) {
        if (mCanRun.getAsBoolean()){
            return desiredSpeed;
        }

        return 0;
    }
}
