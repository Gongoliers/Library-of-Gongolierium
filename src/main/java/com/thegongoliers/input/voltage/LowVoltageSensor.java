package com.thegongoliers.input.voltage;

public class LowVoltageSensor extends VoltageTripSensor {

	/**
	 * Triggers when the voltage dips below a threshold
	 * 
	 * @param voltageSensor
	 *            A voltage sensor
	 * @param threshold
	 *            A threshold to trigger in Volts.
	 */
	public LowVoltageSensor(VoltageSensor voltageSensor, double threshold) {
		super(voltageSensor, threshold);
	}

	@Override
	/**
	 * Determines if the voltage is less or equal to the threshold
	 */
	public boolean isTriggered() {
		return sensor.getVoltage() <= thresh;
	}
}
