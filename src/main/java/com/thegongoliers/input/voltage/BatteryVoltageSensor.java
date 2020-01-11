package com.thegongoliers.input.voltage;

import edu.wpi.first.wpilibj.RobotController;

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
		return RobotController.getBatteryVoltage();
	}

}
