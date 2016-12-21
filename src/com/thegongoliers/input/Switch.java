package com.thegongoliers.input;

public interface Switch {
	boolean isTriggered();

	public static Switch and(Switch s1, Switch s2) {
		return () -> (s1.isTriggered() && s1.isTriggered());
	}

	public static Switch or(Switch s1, Switch s2) {
		return () -> (s1.isTriggered() || s2.isTriggered());
	}

	default public Switch invert() {
		return () -> !isTriggered();
	}
}
