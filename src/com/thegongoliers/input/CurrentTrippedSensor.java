package com.thegongoliers.input;

public class CurrentTrippedSensor implements Switch {

	private CurrentSensor sensor;
	private double thresh;

	/**
	 * Triggers when the current exceeds a threshold
	 * 
	 * @param currentSensor
	 *            A current sensor
	 * @param threshold
	 *            A threshold to trigger in Amps.
	 */
	public CurrentTrippedSensor(CurrentSensor currentSensor, double threshold) {
		sensor = currentSensor;
		thresh = threshold;
	}

	@Override
	/**
	 * Determines if the current is greater or equal to the threshold
	 */
	public boolean isTriggered() {
		return sensor.getCurrent() >= thresh;
	}
}
