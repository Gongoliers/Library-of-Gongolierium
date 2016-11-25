package com.thegongoliers.input.camera;

public class TargetNotFoundException extends Exception {

	private static final long serialVersionUID = -8838872167969870003L;

	public TargetNotFoundException() {
		super("A target was not found");
	}
}
