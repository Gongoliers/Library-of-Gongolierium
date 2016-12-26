package com.thegongoliers.input;

import edu.wpi.first.wpilibj.ControllerPower;

public class BatteryVoltageSensor implements VoltageSensor {

	@Override
	public double getVoltage() {
		return ControllerPower.getInputVoltage();
	}

}
