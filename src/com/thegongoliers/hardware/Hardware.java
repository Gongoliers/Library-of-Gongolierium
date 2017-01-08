package com.thegongoliers.hardware;

import com.thegongoliers.output.JoinedSpeedController;

import edu.wpi.first.wpilibj.SpeedController;

import edu.wpi.first.wpilibj.interfaces.Gyro;

public class Hardware {

	/**
	 * Join motors together to control synchronously with a single
	 * SpeedController.
	 * 
	 * @param controllers
	 *            The controllers to join together.
	 * @return A SpeedController which combines all the SpeedControllers
	 *         together.
	 */
	public static SpeedController joinMotors(SpeedController... controllers) {
		return new JoinedSpeedController(controllers);
	}

	/**
	 * Invert the direction of the Gyro.
	 * 
	 * @param gyro
	 *            The Gyro to invert.
	 * @return The inverted Gyro.
	 */
	public static Gyro invertGyro(Gyro gyro) {
		return new Gyro() {

			@Override
			public void reset() {
				gyro.reset();
			}

			@Override
			public double getRate() {
				return -gyro.getRate();
			}

			@Override
			public double getAngle() {
				return -gyro.getAngle();
			}

			@Override
			public void free() {
				gyro.free();
			}

			@Override
			public void calibrate() {
				gyro.calibrate();
			}
		};
	}

}
