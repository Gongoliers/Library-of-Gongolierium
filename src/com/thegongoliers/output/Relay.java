package com.thegongoliers.output;

public interface Relay {
	Relay on();

	Relay off();

	boolean isOn();

	boolean isOff();
}
