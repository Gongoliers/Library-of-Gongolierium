package com.thegongoliers.input.voltage;

import com.thegongoliers.input.switches.ResettableSwitch;

public class VoltageSpikeSensor implements ResettableSwitch {

	private final VoltageTripSensor sensor;
	private boolean wasTriggered = false;

	/**
	 * Triggers after a spike in the voltage
	 * 
	 * @param voltageTripSensor
	 *            The voltage trip sensor (HighVoltageSensors detect upward spikes,
	 *            LowVoltageSensors downward spikes)
	 */
	public VoltageSpikeSensor(VoltageTripSensor voltageTripSensor) {
		sensor = voltageTripSensor;
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
