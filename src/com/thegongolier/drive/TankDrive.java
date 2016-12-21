package com.thegongolier.drive;

import com.thegongoliers.output.DriveTrainInterface;
import com.thegongoliers.output.Relay;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

public class TankDrive implements DriveTrainInterface {
	private RobotDrive robotDrive;
	private Relay highGear;

	public TankDrive(SpeedController left, SpeedController right, Relay relay) {
		robotDrive = new RobotDrive(left, right);
		highGear = relay;
	}

	public TankDrive(SpeedController left, SpeedController right) {
		this(left, right, null);
	}

	@Override
	public void stop() {
		robotDrive.stopMotor();
		lowGear();
	}

	public void arcade(double speed, double rotation) {
		robotDrive.arcadeDrive(speed, rotation);
	}

	public void arcade(double speed, double rotation, boolean squared) {
		robotDrive.arcadeDrive(speed, rotation, squared);
	}

	public void tank(double left, double right) {
		robotDrive.tankDrive(left, right);
	}

	public void tank(double left, double right, boolean squared) {
		robotDrive.tankDrive(left, right, squared);
	}

	public void highGear() {
		if (highGear != null)
			highGear.on();
	}

	public void lowGear() {
		if (highGear != null)
			highGear.off();
	}

	@Override
	public void forward(double speed) {
		robotDrive.drive(-speed, 0);
	}

	@Override
	public void reverse(double speed) {
		robotDrive.drive(speed, 0);
	}

	@Override
	public void rotateCounterClockwise(double speed) {
		robotDrive.arcadeDrive(0, -speed);
	}

	@Override
	public void rotateClockwise(double speed) {
		robotDrive.arcadeDrive(0, speed);
	}

}
