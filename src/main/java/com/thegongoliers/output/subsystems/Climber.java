package com.thegongoliers.output.subsystems;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import com.thegongoliers.output.interfaces.ClimberInterface;

import edu.wpi.first.wpilibj.SpeedController;

public class Climber implements ClimberInterface {

    private SpeedController motor;
    private DoubleSupplier position;
    private BooleanSupplier atTop, atBottom;

    /**
     * Create a climber mechanism using the given motor and sensors.
     *
     * @param motor    The climber's motor.
     * @param position The position of the climber.
     * @param atTop    Determines if the climber is at the top.
     * @param atBottom Determines if the climber is at the bottom.
     */
    public Climber(SpeedController motor, DoubleSupplier position, BooleanSupplier atTop, BooleanSupplier atBottom) {
        this.motor = motor;
        this.position = position;
        this.atTop = atTop;
        this.atBottom = atBottom;
    }

    /**
     * Create a climber mechanism using the given motor.
     *
     * @param motor The climber's motor.
     */
    public Climber(SpeedController motor) {
        this(motor, () -> 0.0, () -> false, () -> false);
    }

    /**
     * Create a climber mechanism using the given motor and sensors.
     *
     * @param motor    The climber's motor.
     * @param atTop    Determines if the climber is at the top.
     * @param atBottom Determines if the climber is at the bottom.
     */
    public Climber(SpeedController motor, BooleanSupplier atTop, BooleanSupplier atBottom) {
        this(motor, () -> atTop.getAsBoolean() ? 1 : 0, atTop, atBottom);
    }

    @Override
    public void stop() {
        motor.stopMotor();
    }

    @Override
    public void up(double speed) {
        motor.set(speed);
    }

    @Override
    public boolean isAtBottom() {
        return atBottom.getAsBoolean();
    }

    @Override
    public boolean isAtTop() {
        return atTop.getAsBoolean();
    }

    @Override
    public double getPosition() {
        return position.getAsDouble();
    }

}
