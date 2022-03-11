package com.thegongoliers.output.actuators;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public abstract class BaseMotorControllerDecorator implements MotorController {

    protected final MotorController mSpeedController;

    public BaseMotorControllerDecorator(MotorController speedController) {
        mSpeedController = speedController;
    }

    @Override
    public void set(double speed) {
        mSpeedController.set(speed);
    }

    @Override
    public double get() {
        return mSpeedController.get();
    }

    @Override
    public void setInverted(boolean isInverted) {
        mSpeedController.setInverted(isInverted);
    }

    @Override
    public boolean getInverted() {
        return mSpeedController.getInverted();
    }

    @Override
    public void disable() {
        mSpeedController.disable();
    }

    @Override
    public void stopMotor() {
        mSpeedController.stopMotor();
    }

}
