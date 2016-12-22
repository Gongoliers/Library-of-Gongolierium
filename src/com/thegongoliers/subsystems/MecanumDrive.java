package com.thegongoliers.subsystems;

import com.thegongoliers.output.MecanumDriveTrainInterface;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class MecanumDrive extends DriveTrain implements MecanumDriveTrainInterface {

	private RobotDrive robotDrive;
	private Gyro gyro;
	private Command defaultCmd;

	public MecanumDrive(SpeedController frontLeft, SpeedController rearLeft, SpeedController frontRight,
			SpeedController rearRight, Gyro gyro) {
		robotDrive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
		this.gyro = gyro;
	}

	public MecanumDrive(SpeedController frontLeft, SpeedController rearLeft, SpeedController frontRight,
			SpeedController rearRight) {
		this(frontLeft, rearLeft, frontRight, rearRight, null, null);
	}

	public MecanumDrive(SpeedController frontLeft, SpeedController rearLeft, SpeedController frontRight,
			SpeedController rearRight, Gyro gyro, Command defaultCmd) {
		robotDrive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
		this.gyro = gyro;
		this.defaultCmd = defaultCmd;
	}

	public MecanumDrive(SpeedController frontLeft, SpeedController rearLeft, SpeedController frontRight,
			SpeedController rearRight, Command defaultCmd) {
		this(frontLeft, rearLeft, frontRight, rearRight, null, defaultCmd);
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

	@Override
	public void stop() {
		robotDrive.stopMotor();
	}

	@Override
	public void left(double speed) {
		robotDrive.mecanumDrive_Cartesian(-speed, 0, 0, 0);
	}

	@Override
	public void right(double speed) {
		robotDrive.mecanumDrive_Cartesian(speed, 0, 0, 0);
	}

	public void cartesian(double x, double y, double rotation) {
		robotDrive.mecanumDrive_Cartesian(x, y, rotation, gyro != null ? gyro.getAngle() : 0);
	}

	public void polar(double magnitude, double direction, double rotation) {
		robotDrive.mecanumDrive_Polar(magnitude, direction, rotation);
	}
	
	@Override
	public void setDefaultCommand(Command command) {
		super.setDefaultCommand(command);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(defaultCmd);
	}

}
