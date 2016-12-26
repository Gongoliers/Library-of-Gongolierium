package com.thegongoliers.input;

public class CurrentSpikeSensor implements ResettableSwitch {

	private CurrentTrippedSensor sensor;
	private boolean wasTriggered = false;

	/**
	 * Triggers after a spike in the current
	 * 
	 * @param currentSensor
	 *            A current sensor
	 * @param threshold
	 *            The threshold of the spike in Amps.
	 */
	public CurrentSpikeSensor(CurrentSensor currentSensor, double threshold) {
		sensor = new CurrentTrippedSensor(currentSensor, threshold);
	}

	@Override
	/**
	 * Detects if a current spike just occurred. Is true right after the end of
	 * the spike.
	 */
	public boolean isTriggered() {
		if (wasTriggered && !sensor.isTriggered())
			return true;
		wasTriggered = sensor.isTriggered();
		return false;
	}

	/**
	 * Reset the current spike sensor to prepare it to sense a new spike.
	 */
	public void reset() {
		wasTriggered = false;
	}

}
