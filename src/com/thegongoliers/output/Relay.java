package com.thegongoliers.output;

public interface Relay {
	void on();

	void off();

	boolean isOn();

	boolean isOff();
}
