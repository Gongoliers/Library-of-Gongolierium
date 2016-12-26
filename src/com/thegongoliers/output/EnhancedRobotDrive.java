package com.thegongoliers.output;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class EnhancedRobotDrive implements Stoppable, MecanumDriveTrainInterface {

	private double relativeAngle = 0;
	private Gyro gyro;
	private double kp = 0.01;
	private RobotDrive robotDrive;
	private Relay transmission;

	EnhancedRobotDrive(SpeedController frontLeftMotor, SpeedController rearLeftMotor, SpeedController frontRightMotor,
			SpeedController rearRightMotor) {
		robotDrive = new RobotDrive(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
	}

	EnhancedRobotDrive(SpeedController leftMotor, SpeedController rightMotor) {
		robotDrive = new RobotDrive(leftMotor, rightMotor);
	}

	public void markRelativeOrientation() {
		relativeAngle = getOrientation();
	}

	public double getFieldOrientation() {
		return getOrientation() % 360;
	}

	private double getOrientation() {
		if (gyro != null)
			return gyro.getAngle();
		return 0;
	}

	public boolean rotateToward(double angle, PID controller) {
		if (gyro != null) {
			controller.continuous = true;
			controller.minInput = 0;
			controller.maxInput = 360;
			arcade(0, controller.getOutput(getFieldOrientation(), angle));
			return controller.isAtTargetPosition(getFieldOrientation(), angle);
		} else {
			return false;
		}
	}

	public void mecanum(double x, double y, double rotation) {
		robotDrive.mecanumDrive_Cartesian(x, y, rotation, 0);
	}

	public void arcade(double speed, double rotation) {
		robotDrive.arcadeDrive(speed, rotation);
	}

	public void tank(double left, double right) {
		robotDrive.tankDrive(left, right);
	}

	public void forward(double speed) {
		arcade(-speed, kp * (relativeAngle - getOrientation()));
	}

	public void backward(double speed) {
		arcade(speed, kp * (relativeAngle - getOrientation()));
	}

	public void strafeLeft(double speed) {
		mecanum(-speed, 0, kp * (relativeAngle - getOrientation()));
	}

	public void strafeRight(double speed) {
		mecanum(speed, 0, kp * (relativeAngle - getOrientation()));
	}

	public void rotateLeft(double speed) {
		arcade(0, -speed);
	}

	public void rotateRight(double speed) {
		arcade(0, speed);
	}

	public void stop() {
		robotDrive.stopMotor();
	}

	public void highGear() {
		if (transmission != null)
			transmission.on();
	}

	public void lowGear() {
		if (transmission != null)
			transmission.off();
	}

	public boolean isInHighGear() {
		return transmission != null && transmission.isOn();
	}

	public boolean isInLowGear() {
		return transmission != null && transmission.isOff();
	}

	public void arcadeStabilized(double speed, double rotation, double rotationThreshold) {
		if (Math.abs(rotation) >= rotationThreshold) {
			arcade(speed, rotation);
			markRelativeOrientation();
		} else {
			arcade(speed, kp * (relativeAngle - getOrientation()));
		}
	}

	public void arcadeStabilized(double speed, double rotation) {
		arcadeStabilized(speed, rotation, 0.1);
	}

	public static class Builder {
		private EnhancedRobotDrive drive;

		public Builder(SpeedController leftFront, SpeedController leftRear, SpeedController rightFront,
				SpeedController rightRear) {
			drive = new EnhancedRobotDrive(leftFront, leftRear, rightFront, rightRear);
		}

		public Builder(SpeedController left, SpeedController right) {
			drive = new EnhancedRobotDrive(left, right);
		}

		public Builder addStabilization(Gyro gyro) {
			drive.gyro = gyro;
			return this;
		}

		public Builder addTransmission(Relay relay) {
			drive.transmission = relay;
			return this;
		}

		public Builder setStabilizationFactor(double kp) {
			drive.kp = kp;
			return this;
		}

		public EnhancedRobotDrive build() {
			return drive;
		}

	}

}
