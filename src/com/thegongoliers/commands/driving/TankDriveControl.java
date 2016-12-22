package com.thegongoliers.commands.driving;

import com.thegongoliers.input.JoystickInput;
import com.thegongoliers.subsystems.TankDrive;

import edu.wpi.first.wpilibj.command.Command;

public class TankDriveControl extends Command {

	private TankDrive drive;
	private JoystickInput left, right;
	private boolean squared = false;

	public TankDriveControl(TankDrive drive, JoystickInput left, JoystickInput right, boolean squared) {
		requires(drive);
		this.drive = drive;
		this.left = left;
		this.right = right;
		this.squared = squared;
	}

	public TankDriveControl(TankDrive drive, JoystickInput left, JoystickInput right) {
		this(drive, left, right, false);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		drive.tank(left.read(), right.read(), squared);
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
