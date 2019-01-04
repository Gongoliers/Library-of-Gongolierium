package com.thegongoliers.input.voltage;

public class HighVoltageSensor extends VoltageTripSensor {

	/**
	 * Triggers when the voltage exceeds a threshold
	 * 
	 * @param voltageSensor
	 *            A voltage sensor
	 * @param threshold
	 *            A threshold to trigger in Volts.
	 */
	public HighVoltageSensor(VoltageSensor voltageSensor, double threshold) {
		super(voltageSensor, threshold);
	}

	/**
	 * Determines if the voltage is greater or equal to the threshold
	 */
	@Override
	public boolean isTriggered() {
		return sensor.getVoltage() >= thresh;
	}
}
