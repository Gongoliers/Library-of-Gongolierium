package com.thegongoliers.input.camera;

/**
 * Thrown if the camera was unable to locate the target.
 *
 */
public class TargetNotFoundException extends Exception {

	private static final long serialVersionUID = -8838872167969870003L;

	/**
	 * Constructs a TargetNotFoundException.
	 */
	public TargetNotFoundException() {
		super("A target was not found");
	}
}
