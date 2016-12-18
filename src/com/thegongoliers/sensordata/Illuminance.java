package com.thegongoliers.sensordata;

public class Illuminance {
	private double illuminance;
	private double variance;

	public Illuminance(double illuminance, double variance) {
		this.illuminance = illuminance;
		this.variance = variance;
	}

	public Illuminance(double illuminance) {
		this(illuminance, 0);
	}

	public double getIlluminance() {
		return illuminance;
	}

	public double getVariance() {
		return variance;
	}
}
