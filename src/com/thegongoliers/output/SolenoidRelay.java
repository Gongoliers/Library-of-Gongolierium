package com.thegongoliers.output;

public class SolenoidRelay implements Relay {
	private EnhancedSolenoid solenoid;

	public SolenoidRelay(EnhancedSolenoid solenoid) {
		this.solenoid = solenoid;
	}

	@Override
	public void on() {
		solenoid.extend();
	}

	@Override
	public void off() {
		solenoid.retract();
	}

	@Override
	public boolean isOn() {
		return solenoid.isExtended();
	}

	@Override
	public boolean isOff() {
		return solenoid.isRetracted();
	}

}
