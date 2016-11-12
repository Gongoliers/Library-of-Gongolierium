package com.thegongoliers.input.camera;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.GetImageSizeResult;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;

public abstract class AbstractCamera implements CameraInterface {

	private double offset = 0.0;
	protected Image frame;

	public AbstractCamera() {
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	}

	public void display() {
		CameraServer.getInstance().setImage(getImage());
	}

	public int getResolution(Axis axis) {
		Image currentImage = getImage();
		GetImageSizeResult size = NIVision.imaqGetImageSize(currentImage);
		if (axis.equals(Axis.X))
			return size.width;
		else
			return size.height;
	}

	public double getHorizontalOffset() {
		return offset;
	}

	public void setHorizontalOffset(double hOffset) {
		this.offset = hOffset;
	}

}
