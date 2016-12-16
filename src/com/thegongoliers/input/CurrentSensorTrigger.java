package com.thegongoliers.input;

import edu.wpi.first.wpilibj.buttons.Trigger;

public class CurrentSensorTrigger extends Trigger {

	private CurrentSensor currentSensor;
	private double threshold;

	/**
	 * Creates a CurrentSensorTrigger which will be active when the current
	 * surpasses a threshold.
	 * 
	 * @param sensor
	 *            A current sensor to monitor
	 * @param threshold
	 *            The threshold in which this trigger is activated when the
	 *            current is high enough.
	 */
	public CurrentSensorTrigger(CurrentSensor sensor, double threshold) {
		currentSensor = sensor;
		this.threshold = threshold;
	}

	@Override
	public boolean get() {
		return currentSensor.getCurrent() > threshold;
	}

}
