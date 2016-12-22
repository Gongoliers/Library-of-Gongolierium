package com.thegongoliers.commands.driving;

import com.thegongoliers.subsystems.MecanumDrive;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveRight extends Command {

	private MecanumDrive drive;
	private double speed;

	public DriveRight(MecanumDrive drive, double speed, double timeout) {
		requires(drive);
		this.drive = drive;
		this.speed = speed;
		setTimeout(timeout);
	}

	public DriveRight(MecanumDrive drive, double speed) {
		requires(drive);
		this.drive = drive;
		this.speed = speed;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		drive.right(speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		drive.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
