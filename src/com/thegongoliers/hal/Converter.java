package com.thegongoliers.hal;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.interfaces.Gyro;

class Converter {
	public static AnalogOutput fromSpeedController(SpeedController controller) {
		return (double value) -> controller.set(value);
	}

	public static AnalogInput fromGyro(Gyro gyro) {
		return gyro::getAngle;
	}

	public static DigitalInput fromWPILibDigitalInput(edu.wpi.first.wpilibj.DigitalInput input) {
		return input::get;
	}

	public static DigitalOutput fromSolenoid(Solenoid solenoid) {
		return solenoid::set;
	}
}
