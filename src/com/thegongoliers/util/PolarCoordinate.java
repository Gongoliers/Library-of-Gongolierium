package com.thegongoliers.util;

public class PolarCoordinate extends Pair {

	/**
	 * A representation of a polar coordinate
	 * @param magnitude The magnitude of the coordinate
	 * @param angle The angle (in radians) of the coordinate
	 */
	public PolarCoordinate(double magnitude, double angle) {
		super(magnitude, angle);
	}

	/**
	 * Get the x component of the polar coordinate
	 * @return The x component
	 */
	public double getXComponent() {
		return getMagnitude() * Math.cos(getAngle());
	}

	/**
	 * Get the y component of the polar coordinate
	 * @return The y component
	 */
	public double getYComponent() {
		return getMagnitude() * Math.sin(getAngle());
	}

	/**
	 * Get the magnitude of the polar coordinate
	 * @return The magnitude
	 */
	public double getMagnitude() {
		return getFirst();
	}

	/**
	 * Get the angle of the polar coordinate
	 * @return The angle
	 */
	public double getAngle() {
		return getSecond();
	}

	/**
	 * Set the magnitude of the polar coordinate
	 * @param mag The new magnitude
	 */
	public void setMagnitude(double mag) {
		setFirst(mag);
	}

	/**
	 * Set the angle of the polar coordinate
	 * @param angle The new angle
	 */
	public void setAngle(double angle) {
		setSecond(angle);
	}
	
	@Override
	public String toString() {
		return "(" + getMagnitude() + ", " + getAngle() + " rad)";
	}

}
