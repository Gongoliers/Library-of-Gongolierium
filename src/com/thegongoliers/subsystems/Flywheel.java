package com.thegongoliers.subsystems;

import com.thegongoliers.output.FlywheelInterface;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Flywheel extends Subsystem implements FlywheelInterface {

	private SpeedController motor;
	private Command defaultCmd;

	public Flywheel(SpeedController motor, Command defaultCmd) {
		this.motor = motor;
		this.defaultCmd = defaultCmd;
	}

	public Flywheel(SpeedController motor) {
		this(motor, null);
	}

	@Override
	public void stop() {
		motor.stopMotor();
	}

	@Override
	public void spinOutward(double speed) {
		motor.set(speed);
	}

	@Override
	public void spinInward(double speed) {
		motor.set(-speed);
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
