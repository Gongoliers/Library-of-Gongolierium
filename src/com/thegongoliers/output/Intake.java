package com.thegongoliers.output;

import edu.wpi.first.wpilibj.SpeedController;

public class Intake implements IntakeInterface {

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
