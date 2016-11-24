package com.thegongoliers.input.camera;

import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;

public class MicrosoftLifeCam extends AbstractCamera {

	private boolean cameraStarted;
	private USBCamera camera;

	public MicrosoftLifeCam(String cameraName) {
		super();
		cameraStarted = false;
		camera = new USBCamera(cameraName);
		camera.openCamera();
	}

	public double getViewAngle() {
		return 60;
	}

	public void setBrightness(int brightness) {
		camera.setBrightness(brightness);
	}

	public int getBrightness() {
		return camera.getBrightness();
	}

	public void setExposureManual(int exposure) {
		camera.setExposureManual(exposure);
	}

	public void setExposureAuto() {
		camera.setExposureAuto();
	}

	public Image getImage() {
		if (!cameraStarted)
			start();
		camera.getImage(frame);
		return frame;
	}

	public void start() {
		camera.startCapture();
		cameraStarted = true;
	}

	public void stop() {
		camera.stopCapture();
		cameraStarted = false;
	}

	public void setFPS(int fps) {
		camera.setFPS(fps);
	}

}
