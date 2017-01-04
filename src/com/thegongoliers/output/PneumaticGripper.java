package com.thegongoliers.output;

import com.thegongoliers.output.interfaces.GripperInterface;

public class PneumaticGripper implements GripperInterface {

	private final EnhancedSolenoid solenoid;

	public PneumaticGripper(EnhancedSolenoid solenoid) {
		this.solenoid = solenoid;
	}

	@Override
	public void stop() {
	}

	@Override
	public void close() {
		solenoid.extend();
	}

	@Override
	public boolean isClosed() {
		return solenoid.isExtended();
	}

	@Override
	public boolean isOpened() {
		return solenoid.isRetracted();
	}

	@Override
	public void open() {
		solenoid.retract();
	}

}