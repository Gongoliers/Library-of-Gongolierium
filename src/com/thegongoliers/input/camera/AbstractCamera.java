package com.thegongoliers.input.camera;

import org.opencv.core.Mat;
import org.opencv.core.Size;

@Deprecated
public abstract class AbstractCamera implements CameraInterface {

	protected Mat frame;

	public AbstractCamera() {
		frame = new Mat();
	}

	public int getResolution(Axis axis) {
		Mat currentImage = getImage();
		Size size = currentImage.size();
		if (axis.equals(Axis.X))
			return (int) size.width;
		else
			return (int) size.height;
	}

}
