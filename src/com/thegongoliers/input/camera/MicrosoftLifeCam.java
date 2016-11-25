package com.thegongoliers.input.camera;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.USBCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.CameraServer;

public class MicrosoftLifeCam extends AbstractCamera {

	private boolean cameraStarted;
	private USBCamera camera;

	public MicrosoftLifeCam(int port) {
		super();
		cameraStarted = false;
		camera = new USBCamera("cam", port);
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

	public Mat getImage() {
		if (!cameraStarted)
			start();
		CvSink cvSink = CameraServer.getInstance().getVideo();
		cvSink.grabFrame(frame);
		return frame;
	}

	public void start() {
		CameraServer.getInstance().startAutomaticCapture(camera);
		cameraStarted = true;
	}

	public void stop() {
		cameraStarted = false;
	}

	public void setFPS(int fps) {
		camera.setFPS(fps);
	}

	public VideoSource getVideoSource() {
		return camera;
	}

}
