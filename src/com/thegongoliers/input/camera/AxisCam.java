package com.thegongoliers.input.camera;

import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.vision.AxisCamera;
import edu.wpi.first.wpilibj.vision.AxisCamera.ExposureControl;

public class AxisCam extends AbstractCamera {

	private AxisCamera camera;

	public AxisCam(String host) {
		super();
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

}
