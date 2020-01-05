package com.thegongoliers.input.orientation;

import com.thegongoliers.math.GMath;

import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public class TiltSensor {
	private final Accelerometer accel;
	private double calibrationPitch, calibrationRoll;
	private static final double CALIBRATION_SAMPLES = 100;

	/**
	 * Calibrate the tilt sensor by setting the current pitch and roll as the zero position.
	 */
	public void calibrate() {
		calibrationRoll = 0;
		calibrationPitch = 0;

		double rollSum = 0;
		double pitchSum = 0;

		for (int i = 0; i < CALIBRATION_SAMPLES; i++) {
			rollSum += getRoll();
			pitchSum += getPitch();
		}

		calibrationRoll = rollSum / CALIBRATION_SAMPLES;
		calibrationPitch = pitchSum / CALIBRATION_SAMPLES;
	}


	/**
	 * Converts an accelerometer reading to tilt.
	 * 
	 * @param accel
	 *            The accelerometer to base tilt off of.
	 */
	public TiltSensor(Accelerometer accel) {
		this.accel = accel;
	}

	/**
	 * Calculates the roll.
	 * @return The roll in degrees.
	 */
	public double getRoll(){
		return Math.toDegrees(Math.atan2(accel.getY(), accel.getZ())) - calibrationRoll;
	}

	/**
	 * Calculates the pitch.
	 * @return The pitch in degrees.
	 */
	public double getPitch() {
		return Math.toDegrees(Math.atan2(-accel.getX(), GMath.magnitude(accel.getY(), accel.getZ()))) - calibrationPitch;
	}



}
