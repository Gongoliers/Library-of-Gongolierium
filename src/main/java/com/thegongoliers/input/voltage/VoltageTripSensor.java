package com.thegongoliers.input.voltage;

import com.thegongoliers.input.switches.Switch;

public abstract class VoltageTripSensor implements Switch {
	protected final VoltageSensor sensor;
	protected final double thresh;

	public VoltageTripSensor(VoltageSensor sensor, double thresh) {
		this.sensor = sensor;
		this.thresh = thresh;
	}

}
