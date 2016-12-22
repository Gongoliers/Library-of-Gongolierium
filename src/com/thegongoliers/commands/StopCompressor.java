package com.thegongoliers.commands;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StopCompressor extends Command {

	private Compressor compressor;

	public StopCompressor(Compressor compressor) {
		this.compressor = compressor;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		compressor.stop();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return compressor.enabled();
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
