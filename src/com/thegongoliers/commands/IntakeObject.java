package com.thegongoliers.commands;

import com.thegongoliers.input.Switch;
import com.thegongoliers.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeObject extends Command {

	private Intake intake;
	private double speed;
	private Switch hasObject;

	public IntakeObject(Intake intake, Switch hasObject) {
		this(intake, 1, hasObject);
	}

	public IntakeObject(Intake intake, double speed, Switch hasObject) {
		requires(intake);
		this.intake = intake;
		this.speed = speed;
		this.hasObject = hasObject;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		intake.in(speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return hasObject.isTriggered();
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
