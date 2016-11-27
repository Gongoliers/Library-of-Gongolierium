package com.thegongoliers.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class RotateToAngle extends Command {

	private double targetAngle;
	private PIDSubsystem robotDriveTrain;

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
