package com.thegongoliers.output;

import edu.wpi.first.wpilibj.RobotDrive;

public class RobotDrivingUtils {

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
