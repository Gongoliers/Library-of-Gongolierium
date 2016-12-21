package com.thegongoliers.input;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.buttons.Trigger;

public class LimitSwitch extends Trigger {

	private DigitalInput input;

	public LimitSwitch(int channel) {
		input = new DigitalInput(channel);
	}

	public boolean isOpen() {
		return get();
	}

	public boolean isClosed() {
		return !get();
	}

	@Override
	public boolean get() {
		return input.get();
	}

}
