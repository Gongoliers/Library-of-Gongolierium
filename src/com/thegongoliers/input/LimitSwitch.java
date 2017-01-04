package com.thegongoliers.input;

import edu.wpi.first.wpilibj.DigitalInput;

public class LimitSwitch implements Switch {

	private DigitalInput input;

	public LimitSwitch(int port) {
		input = new DigitalInput(port);
	}

	@Override
	public boolean isTriggered() {
		return input.get();
	}

}
