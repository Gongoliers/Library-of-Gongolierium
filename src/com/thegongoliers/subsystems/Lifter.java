package com.thegongoliers.subsystems;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import com.thegongoliers.output.LifterInterface;

import edu.wpi.first.wpilibj.SpeedController;

public class Lifter implements LifterInterface {

	private SpeedController motor;
	private DoubleSupplier position;
	private BooleanSupplier atTop, atBottom;

	public Lifter(SpeedController motor, DoubleSupplier position, BooleanSupplier atTop,
			BooleanSupplier atBottom) {
		this.motor = motor;
		this.position = position;
		this.atTop = atTop;
		this.atBottom = atBottom;
	}

	public Lifter(SpeedController motor) {
		this(motor, () -> 0.0, () -> false, () -> false);
	}

	public Lifter(SpeedController motor, BooleanSupplier atTop, BooleanSupplier atBottom) {
		this(motor, () -> 0.0, atTop, atBottom);
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
