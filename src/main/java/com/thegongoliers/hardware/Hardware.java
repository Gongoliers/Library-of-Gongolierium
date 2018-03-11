package com.thegongoliers.hardware;

import com.thegongoliers.input.Switch;

import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.interfaces.Gyro;

import java.util.function.BooleanSupplier;

public class Hardware {

	public static Trigger switchToTrigger(Switch s){
		return new Trigger() {
			@Override
			public boolean get() {
				return s.isTriggered();
			}
		};
	}

	public static Trigger makeTrigger(BooleanSupplier booleanSupplier){
		return new Trigger() {
			@Override
			public boolean get() {
				return booleanSupplier.getAsBoolean();
			}
		};
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
