package com.thegongoliers.util;

public class PolarCoordinates extends Position {

	public PolarCoordinates(double magnitude, double angle) {
		super(magnitude, angle);
	}

	public double getMagnitude() {
		return getX();
	}

	public double getAngle() {
		return getY();
	}

	public void setMagnitude(double mag) {
		setX(mag);
	}

	public void setAngle(double angle) {
		setY(angle);
	}

}
