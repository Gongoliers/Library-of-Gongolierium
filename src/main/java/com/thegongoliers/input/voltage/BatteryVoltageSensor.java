package com.thegongoliers.input.voltage;

import com.thegongoliers.input.PDP;

/**
 * A voltage sensor which monitors the voltage of the battery.
 *
 */
public class BatteryVoltageSensor implements VoltageSensor {


	/**
	 * Monitor the voltage of the battery.
	 */
	public BatteryVoltageSensor(){

	}

	@Override
	public double getVoltage() {
		return PDP.getInstance().getBatteryVoltage();
	}

}
