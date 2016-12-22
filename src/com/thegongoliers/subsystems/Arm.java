package com.thegongoliers.subsystems;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import com.thegongoliers.output.LifterInterface;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public abstract class Arm extends PIDSubsystem implements LifterInterface {

	private SpeedController motor;
	private DoubleSupplier position;
	private BooleanSupplier atTop, atBottom;

	public Arm(SpeedController motor, DoubleSupplier position, BooleanSupplier atTop, BooleanSupplier atBottom,
			double p, double i, double d) {
		super(p, i, d);
		this.motor = motor;
		this.position = position;
		this.atTop = atTop;
		this.atBottom = atBottom;
	}

	public Arm(SpeedController motor, double p, double i, double d) {
		this(motor, () -> 0.0, () -> false, () -> false, p, i, d);
	}

	public Arm(SpeedController motor, BooleanSupplier atTop, BooleanSupplier atBottom, double p, double i, double d) {
		this(motor, () -> 0.0, atTop, atBottom, p, i, d);
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
	protected double returnPIDInput() {
		return position.getAsDouble();
	}

	@Override
	protected void usePIDOutput(double output) {
		motor.set(output);
	}

}
