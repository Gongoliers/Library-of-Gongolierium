package com.thegongoliers.output;

import com.thegongoliers.output.interfaces.Relay;
import edu.wpi.first.wpilibj.Solenoid;

public class EnhancedSolenoid implements Relay {

	private Solenoid solenoid;
	private boolean inverted = false;

	/**
	 * Creates a solenoid at the given channel.
	 * 
	 * @param channel
	 *            The channel that the solenoid is plugged into.
	 */
	public EnhancedSolenoid(int channel) {
		solenoid = new Solenoid(channel);
	}

	/**
	 * Invert the solenoid.
	 */
	public void invert() {
		inverted = true;
	}

	/**
	 * Set the inverted status of the solenoid.
	 * 
	 * @param isInverted
	 *            True if the solenoid is inverted.
	 */
	public void setInverted(boolean isInverted) {
		inverted = isInverted;
	}

	/**
	 * Determines if the solenoid is inverted.
	 * 
	 * @return True if the solenoid is inverted.
	 */
	public boolean isInverted() {
		return inverted;
	}

	/**
	 * Retract the solenoid.
	 */
	public void retract() {
		solenoid.set(inverted);
	}

	/**
	 * Extend the solenoid.
	 */
	public void extend() {
		solenoid.set(!inverted);
	}

	/**
	 * Determines if the solenoid is extended.
	 * 
	 * @return True if the solenoid is extended.
	 */
	public boolean isExtended() {
		return solenoid.get() != inverted;
	}

	/**
	 * Determines if the solenoid is retracted.
	 * 
	 * @return True if the solenoid is retracted.
	 */
	public boolean isRetracted() {
		return solenoid.get() == inverted;
	}

	@Override
	public void on() {
		extend();
	}

	@Override
	public void off() {
		retract();
	}

	@Override
	public boolean isOn() {
		return isExtended();
	}

	@Override
	public boolean isOff() {
		return isRetracted();
	}

}
