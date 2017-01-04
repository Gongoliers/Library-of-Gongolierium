package com.thegongoliers.input.camera;

import org.opencv.core.Range;

public interface LEDColor {
	Range getHue();

	Range getSaturation();

	Range getValue();

	public static LEDColor Green = new LEDColor(){
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
}
