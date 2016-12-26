package com.thegongoliers.input;

public class LowCurrentSensor extends CurrentTripSensor {

	/**
	 * Triggers when the current dips below a threshold
	 * 
	 * @param currentSensor
	 *            A current sensor
	 * @param threshold
	 *            A threshold to trigger in Amps.
	 */
	public LowCurrentSensor(CurrentSensor currentSensor, double threshold) {
		super(currentSensor, threshold);
	}

	@Override
	/**
	 * Determines if the current is less or equal to the threshold
	 */
	public boolean isTriggered() {
		return sensor.getCurrent() <= thresh;
	}
}
