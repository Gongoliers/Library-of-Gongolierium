package com.thegongoliers.input.camera;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.CameraServer;

public class MicrosoftLifeCam extends AbstractCamera {

	private boolean cameraStarted = false;
	private UsbCamera camera;
	CvSink cvSink;;
	private int port;

	public MicrosoftLifeCam(int port) {
		super();
		this.port = port;
		start();
	}

	public void display() {
	}

	public double getViewAngle() {
		return 60;
	}

	public void setBrightness(int brightness) {
		if (cameraStarted && camera.isValid() && camera.isConnected())
			camera.setBrightness(brightness);
	}

	public int getBrightness() {
		if (cameraStarted && camera.isValid() && camera.isConnected())
			return camera.getBrightness();
		return 0;
	}

	public void setExposureManual(int exposure) {
		if (camera.isValid() && camera.isConnected())
			camera.setExposureManual(exposure);
	}

	public void setExposureAuto() {
		if (isReady())
			camera.setExposureAuto();
	}

	private boolean isReady() {
		return cameraStarted && camera.isValid() && camera.isConnected();
	}

	public Mat getImage() {
		if (!cameraStarted)
			start();
		cvSink.grabFrame(frame);
		return frame;
	}

	public void start() {
		camera = CameraServer.getInstance().startAutomaticCapture(port);
		cvSink = CameraServer.getInstance().getVideo();
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

	@Override
	public void setResolution(int width, int height) {
		camera.setResolution(width, height);
	}

}
