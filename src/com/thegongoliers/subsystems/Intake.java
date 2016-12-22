package com.thegongoliers.subsystems;

import com.thegongoliers.output.IntakeInterface;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class Intake extends Subsystem implements IntakeInterface {

	private SpeedController motor;

	public Intake(SpeedController motor) {
		this.motor = motor;
	}

	@Override
	public void stop() {
		motor.stopMotor();
	}

	@Override
	public void in(double speed) {
		motor.set(speed);
	}

	@Override
	public void in() {
		in(1);
	}

	@Override
	public void out(double speed) {
		motor.set(-speed);
	}

	@Override
	public void out() {
		out(1);
	}

}
