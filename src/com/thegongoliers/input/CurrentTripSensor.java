package com.thegongoliers.input;

public abstract class CurrentTripSensor implements Switch {

	protected CurrentSensor sensor;
	protected double thresh;

	public CurrentTripSensor(CurrentSensor sensor, double thresh) {
		this.sensor = sensor;
		this.thresh = thresh;
	}

}
