package com.thegongoliers.subsystems;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import com.thegongoliers.output.LifterInterface;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SimpleElevator extends Subsystem implements LifterInterface {

	private SpeedController motor;
	private DoubleSupplier position;
	private BooleanSupplier atTop, atBottom;
	private Command defaultCmd;

	public SimpleElevator(SpeedController motor, DoubleSupplier position, BooleanSupplier atTop,
			BooleanSupplier atBottom, Command defaultCmd) {
		this.motor = motor;
		this.position = position;
		this.atTop = atTop;
		this.atBottom = atBottom;
		this.defaultCmd = defaultCmd;
	}

	public SimpleElevator(SpeedController motor, Command defaultCmd) {
		this(motor, () -> 0.0, () -> false, () -> false, defaultCmd);
	}

	public SimpleElevator(SpeedController motor, BooleanSupplier atTop, BooleanSupplier atBottom, Command defaultCmd) {
		this(motor, () -> 0.0, atTop, atBottom, defaultCmd);
	}

	public SimpleElevator(SpeedController motor) {
		this(motor, () -> 0.0, () -> false, () -> false, null);
	}

	public SimpleElevator(SpeedController motor, BooleanSupplier atTop, BooleanSupplier atBottom) {
		this(motor, () -> 0.0, atTop, atBottom, null);
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

	@Override
	public void setDefaultCommand(Command command) {
		super.setDefaultCommand(command);
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(defaultCmd);
	}

}
