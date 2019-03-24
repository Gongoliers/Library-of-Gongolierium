package com.thegongoliers.commands;

import com.thegongoliers.output.interfaces.Drivetrain;
import com.thegongoliers.pathFollowing.controllers.MotionController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * A command to rotate the robot by an angle
 *
 */
public class RotateToAngle extends Command {

	private double targetAngle;
	private Drivetrain drivetrain;
	private Gyro gyro;
	private MotionController controller;

	/**
	 * Default constructor
	 *
	 * @param subsystem the subsystem to require
	 * @param drivetrain the drivetrain
	 * @param gyro the gyroscope
	 * @param controller the motion controller which determines percent power given a target angle
	 * @param targetAngle the target angle of rotation, relative to the current position
	 */
	public RotateToAngle(Subsystem subsystem, Drivetrain drivetrain, Gyro gyro, MotionController controller, double targetAngle) {
		requires(subsystem);
		this.targetAngle = targetAngle;
		this.drivetrain = drivetrain;
		this.gyro = gyro;
		this.controller = controller;
	}

	@Override
	protected void initialize() {
		targetAngle += gyro.getAngle();
	}

	@Override
	protected void execute() {
		double pwm = controller.calculate(gyro.getAngle(), targetAngle);
		drivetrain.arcade(0, pwm);
	}

	@Override
	protected boolean isFinished() {
		return controller.isOnTarget(gyro.getAngle(), targetAngle);
	}

	@Override
	protected void end() {
		drivetrain.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}

}
