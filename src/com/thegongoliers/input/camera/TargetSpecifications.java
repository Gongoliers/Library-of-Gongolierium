package com.thegongoliers.input.camera;

import org.opencv.core.Range;

public interface TargetSpecifications {
	Range getHue();

	Range getSaturation();

	Range getValue();

	double getWidth();

	double getHeight();

	double getArea();
}
