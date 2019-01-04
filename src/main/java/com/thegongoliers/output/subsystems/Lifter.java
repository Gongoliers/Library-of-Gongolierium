package com.thegongoliers.output.subsystems;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import com.thegongoliers.output.interfaces.LifterInterface;

import edu.wpi.first.wpilibj.SpeedController;

public class Lifter implements LifterInterface {

	private SpeedController motor;
	private DoubleSupplier position;
	private BooleanSupplier atTop, atBottom;

	/**
	 * Create a lifter mechanism using the given motor and sensors.
	 * 
	 * @param motor
	 *            The lifter's motor.
	 * @param position
	 *            The position of the lifter.
	 * @param atTop
	 *            Determines if the lifter is at the top.
	 * @param atBottom
	 *            Determines if the lifter is at the bottom.
	 */
	public Lifter(SpeedController motor, DoubleSupplier position, BooleanSupplier atTop, BooleanSupplier atBottom) {
		this.motor = motor;
		this.position = position;
		this.atTop = atTop;
		this.atBottom = atBottom;
	}

	/**
	 * Create a lifter mechanism using the given motor.
	 * 
	 * @param motor
	 *            The lifter's motor.
	 */
	public Lifter(SpeedController motor) {
		this(motor, () -> 0.0, () -> false, () -> false);
	}

	/**
	 * Create a lifter mechanism using the given motor and sensors.
	 * 
	 * @param motor
	 *            The lifter's motor.
	 * @param atTop
	 *            Determines if the lifter is at the top.
	 * @param atBottom
	 *            Determines if the lifter is at the bottom.
	 */
	public Lifter(SpeedController motor, BooleanSupplier atTop, BooleanSupplier atBottom) {
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
	public void down(double speed) {
		motor.set(-speed);
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
