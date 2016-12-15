package com.thegongoliers.input;

import edu.wpi.first.wpilibj.buttons.Trigger;

public class CurrentSensorTrigger extends Trigger {

	private CurrentSensor currentSensor;
	private double threshold;

	public CurrentSensorTrigger(CurrentSensor sensor, double threshold) {
		currentSensor = sensor;
		this.threshold = threshold;
	}

	@Override
	public boolean get() {
		return currentSensor.getCurrent() > threshold;
	}

}
