package com.thegongoliers.output;

import com.thegongoliers.annotations.Untested;
import com.thegongoliers.input.PDP;
import com.thegongoliers.input.current.CurrentSensor;
import com.thegongoliers.math.MathExt;
import com.thegongoliers.output.interfaces.IMotor;
import edu.wpi.first.wpilibj.SpeedController;

@Untested
public class Motor implements IMotor {

    private enum ControlType {
        Voltage, Bus
    }

    private ControlType currentControlType = ControlType.Bus;
    private SpeedController controller;
    private CurrentSensor currentSensor;
    private Direction currentDirection = Direction.Stopped;


    public Motor(SpeedController controller, int port){
        this.controller = controller;
        this.currentSensor = PDP.getInstance().getCurrentSensor(port);
    }


    @Override
    public void setVoltage(double voltage, Direction direction) {
        this.currentControlType = ControlType.Voltage;
        double totalVoltage = PDP.getInstance().getBatteryVoltage();
        double proportion = Math.abs(voltage) / totalVoltage;
        proportion = MathExt.toRange(proportion, 0, 1);
        if (direction == Direction.Backward){
            proportion *= -1;
        } else if (direction == Direction.Stopped){
            proportion = 0;
        }

        this.currentDirection = direction;
        controller.set(proportion);
    }

    @Override
    public void setVoltage(double voltage) {
        Direction d;
        if(voltage == 0){
            d = Direction.Stopped;
        } else if (voltage > 0){
            d = Direction.Forward;
        } else {
            d = Direction.Backward;
        }

        setVoltage(Math.abs(voltage), d);
    }

    @Override
    public double getVoltage() {
        return controller.get() * PDP.getInstance().getBatteryVoltage();
    }

    @Override
    public void setBusPercentage(double percentage, Direction direction) {
        this.currentControlType = ControlType.Bus;

        double proportion = MathExt.toRange(Math.abs(percentage) / 100.0, 0, 1);

        if (direction == Direction.Backward){
            proportion *= -1;
        } else if (direction == Direction.Stopped){
            proportion = 0;
        }

        this.currentDirection = direction;
        controller.set(proportion);
    }

    @Override
    public void setBusPercentage(double percentage) {
        Direction d;
        if(percentage == 0){
            d = Direction.Stopped;
        } else if (percentage > 0){
            d = Direction.Forward;
        } else {
            d = Direction.Backward;
        }

        setBusPercentage(Math.abs(percentage), d);
    }

    @Override
    public double getBusPercentage() {
        return Math.abs(controller.get()) * 100;
    }

    @Override
    public double getCurrent() {
        return currentSensor.getCurrent();
    }

    @Override
    public Direction getDirection() {
        return currentDirection;
    }

    @Override
    public void setInverted(boolean inverted) {
        controller.setInverted(inverted);
    }

    @Override
    public boolean isInverted() {
        return controller.getInverted();
    }

    @Override
    public void stop() {
        this.currentDirection = Direction.Stopped;
        controller.set(0);
    }

    public ControlType getControlType(){
        return currentControlType;
    }

}
