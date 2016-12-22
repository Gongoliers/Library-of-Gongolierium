package com.thegongoliers.commands.driving;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * A command to rotate the robot to face a specific angle.
 *
 */
public class RotateToAngle extends Command {

	private double targetAngle;
	private PIDSubsystem robotDriveTrain;

	/**
	 * Constructs a rotation command.
	 * 
	 * @param robotDriveTrain
	 *            A PIDSubsystem which can be controlled by the gyroscope.
	 * @param targetAngle
	 *            The amount of degrees from the current position to rotate.
	 */
	public RotateToAngle(PIDSubsystem robotDriveTrain, double targetAngle) {
		requires(robotDriveTrain);
		this.targetAngle = targetAngle;
		this.robotDriveTrain = robotDriveTrain;
	}

	@Override
	protected void initialize() {
		robotDriveTrain.enable();
		robotDriveTrain.setSetpointRelative(targetAngle);
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return robotDriveTrain.onTarget();
	}

	@Override
	protected void end() {
		robotDriveTrain.disable();
	}

	@Override
	protected void interrupted() {
		end();
	}

}
