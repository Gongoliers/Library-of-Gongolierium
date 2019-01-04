package com.thegongoliers.hardware;

import com.thegongoliers.input.switches.Switch;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.interfaces.Gyro;

import java.util.function.BooleanSupplier;

/**
 * Miscellaneous hardware functions.
 */
public class Hardware {

	/**
	 * Creates a button from a boolean supplier.
	 * @param booleanSupplier The boolean supplier to become a button.
	 * @return The button which is pressed when the boolean supplier is true.
	 */
	public static Button makeButton(BooleanSupplier booleanSupplier){
		return new Button() {
			@Override
			public boolean get() {
				return booleanSupplier.getAsBoolean();
			}
		};
	}

	/**
	 * Creates a button from a trigger.
	 * @param trigger The trigger to become a button.
	 * @return The button which is pressed when the trigger is triggered.
	 */
	public static Button triggerToButton(Trigger trigger){
		return Hardware.makeButton(trigger::get);
	}

	/**
	 * Creates a trigger from a switch.
	 * @param aSwitch The switch to become a trigger.
	 * @return The trigger which is triggered when the switch is triggered.
	 */
	public static Trigger switchToTrigger(Switch aSwitch){
		return Hardware.makeTrigger(aSwitch::isTriggered);
	}

	/**
	 * Creates a trigger from a boolean supplier.
	 * @param booleanSupplier The boolean supplier to become a trigger.
	 * @return The trigger which is triggered when the boolean supplier is true.
	 */
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
			public void close() throws Exception {
				gyro.close();
			}

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
