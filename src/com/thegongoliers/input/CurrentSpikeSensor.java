package com.thegongoliers.input;

public class CurrentSpikeSensor implements ResettableSwitch {

	private CurrentTripSensor sensor;
	private boolean wasTriggered = false;

	/**
	 * Triggers after a spike in the current
	 * 
	 * @param currentTripSensor
	 *            The current trip sensor (HighCurrentSensors detect upward spikes,
	 *            LowCurrentSensors downward spikes)
	 */
	public CurrentSpikeSensor(CurrentTripSensor currentTripSensor) {
		sensor = currentTripSensor;
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
