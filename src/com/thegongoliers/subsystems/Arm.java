package com.thegongoliers.subsystems;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import com.thegongoliers.output.LifterInterface;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class Arm extends PIDSubsystem implements LifterInterface {

	private SpeedController motor;
	private DoubleSupplier position;
	private BooleanSupplier atTop, atBottom;
	private Command defaultCmd;

	public Arm(SpeedController motor, DoubleSupplier position, BooleanSupplier atTop, BooleanSupplier atBottom,
			Command defaultCmd, double p, double i, double d) {
		super(p, i, d);
		this.motor = motor;
		this.position = position;
		this.atTop = atTop;
		this.atBottom = atBottom;
		this.defaultCmd = defaultCmd;
	}

	public Arm(SpeedController motor, Command defaultCmd, double p, double i, double d) {
		this(motor, () -> 0.0, () -> false, () -> false, defaultCmd, p, i, d);
	}

	public Arm(SpeedController motor, BooleanSupplier atTop, BooleanSupplier atBottom, Command defaultCmd,
			double p, double i, double d) {
		this(motor, () -> 0.0, atTop, atBottom, defaultCmd, p, i, d);
	}

	public Arm(SpeedController motor, double p, double i, double d) {
		this(motor, () -> 0.0, () -> false, () -> false, null, p, i, d);
	}

	public Arm(SpeedController motor, BooleanSupplier atTop, BooleanSupplier atBottom, double p, double i,
			double d) {
		this(motor, () -> 0.0, atTop, atBottom, null, p, i, d);
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
	public void setDefaultCommand(Command command) {
		super.setDefaultCommand(command);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(defaultCmd);
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
