package com.thegongoliers.sensordata;

public class Illuminance {
	public double illuminance, variance;

	public Illuminance(double illuminance, double variance) {
		this.illuminance = illuminance;
		this.variance = variance;
	}

	public Illuminance(double illuminance) {
		this(illuminance, 0);
	}

}
