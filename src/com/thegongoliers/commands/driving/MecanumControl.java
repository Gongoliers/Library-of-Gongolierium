package com.thegongoliers.commands.driving;

import com.thegongoliers.input.JoystickInput;
import com.thegongoliers.subsystems.MecanumDrive;

import edu.wpi.first.wpilibj.command.Command;

public class MecanumControl extends Command {

	private MecanumDrive drive;
	private JoystickInput xSpeed, ySpeed, rotation;

	public MecanumControl(MecanumDrive drive, JoystickInput xSpeed, JoystickInput ySpeed, JoystickInput rotation) {
		requires(drive);
		this.drive = drive;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.rotation = rotation;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		drive.cartesian(xSpeed.read(), ySpeed.read(), rotation.read());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		drive.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
