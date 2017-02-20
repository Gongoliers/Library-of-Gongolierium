package com.thegongoliers.input.voltage;

import edu.wpi.first.wpilibj.ControllerPower;

/**
 * A voltage sensor which monitors the voltage of the battery.
 *
 */
public class BatteryVoltageSensor implements VoltageSensor {

	@Override
	public double getVoltage() {
		return ControllerPower.getInputVoltage();
	}

}
