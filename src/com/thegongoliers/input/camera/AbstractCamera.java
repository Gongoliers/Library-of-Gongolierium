package com.thegongoliers.input.camera;

import org.opencv.core.Mat;
import org.opencv.core.Size;

import edu.wpi.first.wpilibj.CameraServer;

public abstract class AbstractCamera implements CameraInterface {

	private double offset = 0.0;
	protected Mat frame;

	public AbstractCamera() {
		frame = new Mat();
	}

	public void display() {
		CameraServer.getInstance().addCamera(getVideoSource());
	}

	public int getResolution(Axis axis) {
		Mat currentImage = getImage();
		Size size = currentImage.size();
		if (axis.equals(Axis.X))
			return (int) size.width;
		else
			return (int) size.height;
	}

	public double getHorizontalOffset() {
		return offset;
	}

	public void setHorizontalOffset(double hOffset) {
		this.offset = hOffset;
	}

}
