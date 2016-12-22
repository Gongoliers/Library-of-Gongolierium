package com.thegongoliers.commands;

import com.thegongoliers.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeOut extends Command {

	private Intake intake;
	private double speed;

	public IntakeOut(Intake intake) {
		this(intake, 1);
	}
	
	public IntakeOut(Intake intake, double speed) {
		requires(intake);
		this.intake = intake;
		this.speed = speed;
	}
	
	public IntakeOut(Intake intake, double speed, double timeout){
		this(intake, speed);
		setTimeout(timeout);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		intake.out(speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		intake.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
