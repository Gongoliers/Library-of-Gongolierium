package com.thegongoliers.input.camera;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.GetImageSizeResult;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.thegongoliers.input.camera.CameraInterface.Axis;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.image.RGBImage;
import edu.wpi.first.wpilibj.vision.AxisCamera;
import edu.wpi.first.wpilibj.vision.USBCamera;
import edu.wpi.first.wpilibj.vision.AxisCamera.ExposureControl;

public class AxisCam implements CameraInterface {

	private AxisCamera camera;
	private Image frame;
	private double offset = 0.0;
	
	public AxisCam(String host) {
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		camera = new AxisCamera(host);
	}

	@Override
	public double getViewAngle() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setBrightness(int brightness) {
		camera.writeBrightness(brightness);
	}

	@Override
	public int getBrightness() {
		return camera.getBrightness();
	}

	@Override
	public void setExposureManual(int exposure) {
		// TODO
		camera.writeExposureControl(ExposureControl.kHold);
	}

	@Override
	public void setExposureAuto() {
		camera.writeExposureControl(ExposureControl.kAutomatic);
	}

	@Override
	public Image getImage() {
		camera.getImage(frame);
		return frame;
	}

	@Override
	public void start() {
		// TODO
	}

	@Override
	public void stop() {
		// TODO
	}

	@Override
	public void setFPS(int fps) {
		camera.writeMaxFPS(fps);
	}

	@Override
	public void display() {
		CameraServer.getInstance().setImage(getImage());
	}

	@Override
	public int getResolution(Axis axis) {
		Image currentImage = getImage();
		GetImageSizeResult size = NIVision.imaqGetImageSize(currentImage);
		if (axis.equals(Axis.X))
			return size.width;
		else
			return size.height;
	}

	@Override
	public double getHorizontalOffset() {
		return 0;
	}
	
	@Override
	public void setHorizontalOffset(double hOffset) {
		this.offset = hOffset;
	}

}
