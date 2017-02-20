package com.thegongoliers.input.camera;

import org.opencv.core.Range;

/**
 * Represents the color of an LED in HSV
 *
 */
public interface LEDColor {

	/**
	 * Gets the hue of the LED.
	 * 
	 * @return The LED's hue.
	 */
	Range getHue();

	/**
	 * Gets the saturation of the LED.
	 * 
	 * @return The LED's saturation.
	 */
	Range getSaturation();

	/**
	 * Gets the value of the LED.
	 * 
	 * @return The LED's value.
	 */
	Range getValue();

	/**
	 * A green superbright LED ring which produces light at 518 nm.
	 */
	public static LEDColor Green = new LEDColor() {
		@Override
		public Range getHue() {
			return new Range(75, 125);
		}

		@Override
		public Range getSaturation() {
			return new Range(175, 255);
		}

		@Override
		public Range getValue() {
			return new Range(65, 255);
		}

	};
	
	/**
	 * A green superbright LED ring (variant 2)
	 */
	public static LEDColor GreenV2 = new LEDColor() {
		@Override
		public Range getHue() {
			return new Range(70, 100);
		}

		@Override
		public Range getSaturation() {
			return new Range(170, 255);
		}

		@Override
		public Range getValue() {
			return new Range(40, 255);
		}

	};
}
