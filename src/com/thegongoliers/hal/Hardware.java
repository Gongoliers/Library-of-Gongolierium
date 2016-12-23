package com.thegongoliers.hal;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Solenoid;

public class Hardware {

	// Digital Output
	public static DigitalOutput Solenoid(int port) {
		return Converter.fromSolenoid(new Solenoid(port));
	}

	// Analog Output
	public static AnalogOutput Talon(int port) {
		return Converter.fromSpeedController(new Talon(port));
	}

	public static AnalogOutput CANTalon(int port) {
		return Converter.fromSpeedController(new CANTalon(port));
	}

	public static AnalogOutput Victor(int port) {
		return Converter.fromSpeedController(new Victor(port));
	}

	public static AnalogOutput Jaguar(int port) {
		return Converter.fromSpeedController(new Jaguar(port));
	}

	// Digital Input
	public static DigitalInput LimitSwitch(int port) {
		return Converter.fromWPILibDigitalInput(new edu.wpi.first.wpilibj.DigitalInput(port));
	}

	// Analog Input
	public static AnalogInput Gyroscope(int port) {
		AnalogGyro gyro = new AnalogGyro(port);
		gyro.initGyro();
		return Converter.fromGyro(gyro);
	}

	public static AnalogInput PDPCurrentSensor(int port) {
		PowerDistributionPanel pdp = new PowerDistributionPanel();
		return () -> pdp.getCurrent(port);
	}

	public static AnalogInput PDPTotalCurrentSensor() {
		PowerDistributionPanel pdp = new PowerDistributionPanel();
		return pdp::getTotalCurrent;
	}

	public static AnalogInput PDPTemperatureSensor() {
		PowerDistributionPanel pdp = new PowerDistributionPanel();
		return pdp::getTemperature;
	}

	public static AnalogInput PDPVoltageSensor() {
		PowerDistributionPanel pdp = new PowerDistributionPanel();
		return pdp::getVoltage;
	}
}
