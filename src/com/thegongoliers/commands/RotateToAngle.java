package com.thegongoliers.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;

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
	 *            Preferably a drivetrain.
	 * @param gyroscope
	 *            The gyroscope on the robot.
	 * @param targetAngle
	 *            The amount of degrees from the current position to rotate.
	 */
	public RotateToAngle(PIDSubsystem robotDriveTrain, Gyro gyroscope, double targetAngle) {
		requires(robotDriveTrain);
		this.targetAngle = gyroscope.getAngle() + targetAngle;
		this.robotDriveTrain = robotDriveTrain;
	}

	@Override
	protected void initialize() {
		robotDriveTrain.enable();
	}

	@Override
	protected void execute() {
		robotDriveTrain.setSetpoint(targetAngle);
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
