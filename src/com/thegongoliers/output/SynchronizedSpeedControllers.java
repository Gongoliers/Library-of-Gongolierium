package com.thegongoliers.output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.wpi.first.wpilibj.SpeedController;

public class SynchronizedSpeedControllers implements SpeedController {

    private List<SpeedController> mSpeedControllers;
    private boolean mIsInverted = false;

    public SynchronizedSpeedControllers() {
        mSpeedControllers = new ArrayList<>();
    }

    public SynchronizedSpeedControllers(SpeedController... speedControllers) {
        mSpeedControllers = new ArrayList<>();
        Collections.addAll(mSpeedControllers, speedControllers);
    }

    public SynchronizedSpeedControllers(List<SpeedController> speedControllers) {
        mSpeedControllers = speedControllers;
    }

    public void add(SpeedController speedController) {
        mSpeedControllers.add(speedController);
    }

    public void remove(SpeedController speedController) {
        mSpeedControllers.remove(speedController);
    }

    public void remove(int speedControllerIndex) {
        mSpeedControllers.remove(speedControllerIndex);
    }

    public SpeedController getSpeedController(int speedControllerIndex) {
        return mSpeedControllers.get(speedControllerIndex);
    }

    public int getSpeedControllerIndex(SpeedController speedController) {
        return mSpeedControllers.indexOf(speedController);
    }

    @Override
    public void pidWrite(double output) {
        for (SpeedController speedController : mSpeedControllers) {
            speedController.pidWrite(output);
        }
    }

    public int getNumSpeedControllers() {
        return mSpeedControllers.size();
    }

    @Override
    public double get() {
        if (getNumSpeedControllers() == 0) {
            return 0;
        }
        return mSpeedControllers.get(0).get();
    }

    @Override
    public void set(double speed, byte syncGroup) {
        for (SpeedController speedController : mSpeedControllers) {
            speedController.set(speed, syncGroup);
        }
    }

    @Override
    public void set(double speed) {
        for (SpeedController speedController : mSpeedControllers) {
            speedController.set(speed);
        }
    }

    @Override
    public void setInverted(boolean isInverted) {
        mIsInverted = isInverted;
        for (SpeedController speedController : mSpeedControllers) {
            speedController.setInverted(!speedController.getInverted());
        }
    }

    public void setInverted(boolean isInverted, int speedControllerIndex) {
        mSpeedControllers.get(speedControllerIndex).setInverted(isInverted);
    }

    @Override
    public boolean getInverted() {
        return mIsInverted;
    }

    @Override
    public void disable() {
        for (SpeedController speedController : mSpeedControllers) {
            speedController.disable();
        }
    }

    public void stopMotor() {
        for (SpeedController speedController : mSpeedControllers) {
            speedController.stopMotor();
        }
    }

}
