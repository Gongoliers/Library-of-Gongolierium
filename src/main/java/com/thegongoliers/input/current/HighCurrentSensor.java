package com.thegongoliers.input.current;

public class HighCurrentSensor extends CurrentTripSensor {

	/**
	 * Triggers when the current exceeds a threshold
	 * 
	 * @param currentSensor
	 *            A current sensor
	 * @param threshold
	 *            A threshold to trigger in Amps.
	 */
	public HighCurrentSensor(CurrentSensor currentSensor, double threshold) {
		super(currentSensor, threshold);

	}

	@Override
	/**
	 * Determines if the current is greater or equal to the threshold
	 */
	public boolean isTriggered() {
		return sensor.getCurrent() >= thresh;
	}
}
