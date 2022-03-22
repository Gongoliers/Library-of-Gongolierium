package com.thegongoliers.input.accelerometer;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.interfaces.Accelerometer;

/**
 * Allows a Navx to be referenced as an Accelerometer.
 */
public class NavXAccelerometer implements Accelerometer {

	private AHRS navx;

	public NavXAccelerometer(AHRS navx) {
		this.navx = navx;
	}

	public double getX() {
		return navx.getRawAccelX();
	}

	public double getY() {
		return navx.getRawAccelY();
	}

	public double getZ() {
		return navx.getRawAccelZ();
	}

	public void setRange(Accelerometer.Range range) {
		// TODO
	}

}

