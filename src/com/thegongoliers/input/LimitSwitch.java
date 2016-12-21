package com.thegongoliers.input;

import edu.wpi.first.wpilibj.DigitalInput;

public class LimitSwitch extends DigitalInput {

	public LimitSwitch(int channel) {
		super(channel);
	}

	public boolean isOpen() {
		return get();
	}

	public boolean isClosed() {
		return !get();
	}

}
