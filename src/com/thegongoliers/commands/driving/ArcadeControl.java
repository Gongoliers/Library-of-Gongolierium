package com.thegongoliers.commands.driving;

import com.thegongoliers.input.JoystickInput;
import com.thegongoliers.subsystems.TankDrive;

import edu.wpi.first.wpilibj.command.Command;

public class ArcadeControl extends Command {

	private TankDrive drive;
	private JoystickInput speed, rotation;
	private boolean squared = false;

	public ArcadeControl(TankDrive drive, JoystickInput speed, JoystickInput rotation, boolean squared) {
		requires(drive);
		this.drive = drive;
		this.speed = speed;
		this.rotation = rotation;
		this.squared = squared;
	}

	public ArcadeControl(TankDrive drive, JoystickInput speed, JoystickInput rotation) {
		this(drive, speed, rotation, false);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		drive.arcade(speed.read(), rotation.read(), squared);
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
