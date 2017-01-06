package com.thegongoliers.output;

import edu.wpi.first.wpilibj.Solenoid;

public class EnhancedSolenoid implements Relay {

	private Solenoid solenoid;
	private boolean inverted = false;

	public EnhancedSolenoid(int channel) {
		solenoid = new Solenoid(channel);
	}

	public void invert() {
		inverted = true;
	}

	public void setInverted(boolean isInverted) {
		inverted = isInverted;
	}

	public boolean isInverted() {
		return inverted;
	}

	public void retract() {
		solenoid.set(inverted);
	}

	public void extend() {
		solenoid.set(!inverted);
	}

	public boolean isExtended() {
		return solenoid.get() != inverted;
	}

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
