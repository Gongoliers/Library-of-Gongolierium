package com.thegongoliers.input;

public abstract class VoltageTripSensor implements Switch {
	protected VoltageSensor sensor;
	protected double thresh;

	public VoltageTripSensor(VoltageSensor sensor, double thresh) {
		this.sensor = sensor;
		this.thresh = thresh;
	}

}
