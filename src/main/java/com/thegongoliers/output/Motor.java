package com.thegongoliers.output;

import com.thegongoliers.input.voltage.BatteryVoltageSensor;
import com.thegongoliers.input.voltage.VoltageSensor;
import com.thegongoliers.math.MathExt;
import com.thegongoliers.output.interfaces.IMotor;
import edu.wpi.first.wpilibj.SpeedController;

import java.util.LinkedList;
import java.util.List;

public class Motor implements IMotor {

    private final List<IMotor> followers;

    enum ControlType {
        Voltage, PWM
    }

    private ControlType currentControlType = ControlType.PWM;
    private final SpeedController controller;
    private Direction currentDirection = Direction.Stopped;
    private final VoltageSensor batteryVoltageSensor;


    public Motor(SpeedController controller, VoltageSensor batteryVoltageSensor){
        this.controller = controller;
        this.batteryVoltageSensor = batteryVoltageSensor;
        followers = new LinkedList<>();
    }

    public Motor(SpeedController controller){
        this(controller, new BatteryVoltageSensor());
    }


    @Override
    public void setVoltage(double voltage, Direction direction) {
        this.currentControlType = ControlType.Voltage;
        double totalVoltage = batteryVoltageSensor.getVoltage();
        double proportion = Math.abs(voltage) / totalVoltage;
        proportion = MathExt.toRange(proportion, 0, 1);
        if (direction == Direction.Backward){
            proportion *= -1;
        } else if (direction == Direction.Stopped){
            proportion = 0;
        }

        this.currentDirection = direction;
        controller.set(proportion);
        followers.forEach(motor -> motor.setVoltage(voltage, direction));
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
        return Math.abs(controller.get()) * batteryVoltageSensor.getVoltage();
    }

    @Override
    public void setPWM(double pwm, Direction direction) {
        this.currentControlType = ControlType.PWM;

        double proportion = MathExt.toRange(Math.abs(pwm), 0, 1);

        if (direction == Direction.Backward){
            proportion *= -1;
        } else if (direction == Direction.Stopped){
            proportion = 0;
        }

        this.currentDirection = direction;
        controller.set(proportion);
        followers.forEach(motor -> motor.setPWM(pwm, direction));
    }

    @Override
    public void setPWM(double pwm) {
        Direction d;
        if(pwm == 0){
            d = Direction.Stopped;
        } else if (pwm > 0){
            d = Direction.Forward;
        } else {
            d = Direction.Backward;
        }

        setPWM(Math.abs(pwm), d);
    }

    @Override
    public double getPWM() {
        return Math.abs(controller.get());
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
    public void follow(IMotor motor) {
        if(motor != null)
            motor.addFollower(this);
    }

    @Override
    public void unfollow(IMotor motor) {
        if(motor != null)
            motor.removeFollower(this);
    }

    @Override
    public void addFollower(IMotor motor) {
        if(motor != null && motor != this && !followers.contains(motor)){
            followers.add(motor);
            motor.setPWM(getPWM(), getDirection());
        }
    }

    @Override
    public void removeFollower(IMotor motor) {
        if(motor != null && followers.contains(motor)){
            followers.remove(motor);
            motor.setPWM(0, Direction.Stopped);
        }
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
