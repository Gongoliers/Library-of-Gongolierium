package com.thegongoliers.output;

import edu.wpi.first.wpilibj.SpeedController;

public class Flywheel implements FlywheelInterface {

	private SpeedController motor;

	public Flywheel(SpeedController motor) {
		this.motor = motor;
	}

	@Override
	public void stop() {
		motor.stopMotor();
	}

	@Override
	public void spinOutward(double speed) {
		motor.set(speed);
	}

	@Override
	public void spinInward(double speed) {
		motor.set(-speed);
	}

}
