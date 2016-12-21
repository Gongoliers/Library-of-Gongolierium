package com.thegongoliers.output;

public class Solenoid extends edu.wpi.first.wpilibj.Solenoid {

	private boolean inverted = false;

	public Solenoid(int channel) {
		super(channel);
	}

	public Solenoid(int moduleNumber, int channel) {
		super(moduleNumber, channel);
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
		set(inverted);
	}

	public void extend() {
		set(!inverted);
	}

	public boolean isExtended() {
		return get() != inverted;
	}

	public boolean isRetracted() {
		return get() == inverted;
	}

}
