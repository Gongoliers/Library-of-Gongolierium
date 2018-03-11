package com.thegongoliers.input.voltage;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

/**
 * A voltage sensor which monitors the voltage of the battery.
 *
 */
public class BatteryVoltageSensor implements VoltageSensor {

	private PowerDistributionPanel panel;

	/**
	 * Monitor the voltage of the battery.
	 * @param panel The PDP on the robot.
	 */
	public BatteryVoltageSensor(PowerDistributionPanel panel){
		this.panel = panel;
	}

	@Override
	public double getVoltage() {
		return panel.getVoltage();
	}

}
