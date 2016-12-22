package com.thegongoliers.commands;

import com.thegongoliers.output.LifterInterface;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ElevatorUp extends Command {

	private LifterInterface elevator;
	private double speed;

	public <T extends Subsystem & LifterInterface> ElevatorUp(T elevator, double speed, double timeout) {
		requires(elevator);
		this.elevator = elevator;
		this.speed = speed;
		setTimeout(timeout);
	}

	public <T extends Subsystem & LifterInterface> ElevatorUp(T elevator, double speed) {
		requires(elevator);
		this.elevator = elevator;
		this.speed = speed;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		elevator.up(speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		elevator.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
