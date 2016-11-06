package com.thegongoliers.output;

import edu.wpi.first.wpilibj.RobotDrive;

public class RobotDrivingUtils {

	private static double initialGyroAngle = 0.0;

	/**
	 * Set the initial gyro angle of the robot, used mainly for manual
	 * correction of the gyroStabilizedOperation method.
	 * 
	 * @param angle
	 *            The new initial angle of the robot
	 */
	public static void setInitialGyroAngle(double angle) {
		initialGyroAngle = angle;
	}

	/**
	 * Allows the robot to correct drift using the gyro during normal, operator
	 * controlled driving.
	 * 
	 * @param robotDrive
	 *            The main RobotDrive class for your robot's drivetrain (uses
	 *            arcadeDrive)
	 * @param y
	 *            The speed from -1 to 1 that the robot will go in the y
	 *            direction
	 * @param rotation
	 *            The speed from -1 to 1 that the robot will turn
	 * @param gyroAngle
	 *            The current angle of the robot
	 * @param rotationThreshold
	 *            The minimum amount of joystick rotation input needed to
	 *            manually turn the robot
	 * @param correctionFactor
	 *            The magnitude of the correction that the robot will take to
	 *            drive straight (0.01 worked well in the past)
	 */
	public static void gyroStabalizedOperation(RobotDrive robotDrive, double y, double rotation, double gyroAngle,
			double rotationThreshold, double correctionFactor) {
		if (Math.abs(rotation) >= rotationThreshold) {
			robotDrive.arcadeDrive(y, rotation);
			setInitialGyroAngle(gyroAngle);
		} else {
			robotDrive.arcadeDrive(y, -correctionFactor * (gyroAngle - initialGyroAngle));
		}
	}

	public static void forward(RobotDrive robotDrive, double speed) {
		robotDrive.drive(-speed, 0);
	}

	public static void forward(RobotDrive robotDrive, double speed, double gyroAngle, double correctionFactor) {
		robotDrive.drive(-speed, -correctionFactor * gyroAngle);
	}

	public static void reverse(RobotDrive robotDrive, double speed) {
		robotDrive.drive(speed, 0);
	}

	public static void reverse(RobotDrive robotDrive, double speed, double gyroAngle, double correctionFactor) {
		robotDrive.drive(speed, -correctionFactor * gyroAngle);
	}

	public static void rotateLeft(RobotDrive robotDrive, double speed) {
		robotDrive.arcadeDrive(0, -speed);
	}

	public static void rotateRight(RobotDrive robotDrive, double speed) {
		robotDrive.arcadeDrive(0, speed);
	}

}
