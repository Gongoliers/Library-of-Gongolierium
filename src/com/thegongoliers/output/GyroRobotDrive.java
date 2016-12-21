package com.thegongoliers.output;

import com.thegongoliers.input.Gyroscope;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

public class GyroRobotDrive extends RobotDrive {

	private double relativeAngle = 0;
	private Gyroscope gyro;
	private double kp = 0.01;

	public GyroRobotDrive(SpeedController frontLeftMotor, SpeedController rearLeftMotor,
			SpeedController frontRightMotor, SpeedController rearRightMotor, Gyroscope gyro) {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
		this.gyro = gyro;
	}

	public GyroRobotDrive(SpeedController leftMotor, SpeedController rightMotor, Gyroscope gyro) {
		super(leftMotor, rightMotor);
		this.gyro = gyro;
	}

	public GyroRobotDrive(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor,
			Gyroscope gyro) {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
		this.gyro = gyro;
	}

	public GyroRobotDrive(int leftMotorChannel, int rightMotorChannel, Gyroscope gyro) {
		super(leftMotorChannel, rightMotorChannel);
		this.gyro = gyro;
	}

	public void markRelativePosition() {
		relativeAngle = gyro.getAngle();
	}

	public double getFieldOrientation() {
		return gyro.getHeading();
	}

	public void setKP(double kp) {
		this.kp = kp;
	}

	public void mecanumDrive_Cartesian(double x, double y, double rotation) {
		super.mecanumDrive_Cartesian(x, y, rotation, gyro.getAngle());
	}

	public void stabilizedDrive(double magnitude, double curve) {
		drive(magnitude, kp * (relativeAngle - gyro.getAngle()));
	}

	public void stabilizedArcadeDrive(double speed, double rotation, double rotationThreshold) {
		if (Math.abs(rotation) >= rotationThreshold) {
			arcadeDrive(speed, rotation);
			markRelativePosition();
		} else {
			arcadeDrive(speed, kp * (relativeAngle - gyro.getAngle()));
		}
	}

	public void stabilizedArcadeDrive(double speed, double rotation) {
		stabilizedArcadeDrive(speed, rotation, 0.1);
	}

	public Gyroscope getGyro() {
		return gyro;
	}

}
