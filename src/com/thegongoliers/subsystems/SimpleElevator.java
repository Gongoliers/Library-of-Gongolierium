package com.thegongoliers.subsystems;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import com.thegongoliers.output.LifterInterface;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class SimpleElevator extends Subsystem implements LifterInterface {

	private SpeedController motor;
	private DoubleSupplier position;
	private BooleanSupplier atTop, atBottom;

	public SimpleElevator(SpeedController motor, DoubleSupplier position, BooleanSupplier atTop,
			BooleanSupplier atBottom) {
		this.motor = motor;
		this.position = position;
		this.atTop = atTop;
		this.atBottom = atBottom;
	}

	public SimpleElevator(SpeedController motor) {
		this(motor, () -> 0.0, () -> false, () -> false);
	}

	public SimpleElevator(SpeedController motor, BooleanSupplier atTop, BooleanSupplier atBottom) {
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
