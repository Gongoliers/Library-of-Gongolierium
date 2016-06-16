package org.usfirst.frc.team5112.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.GetImageSizeResult;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class MicrosoftLifeCam implements CameraInterface {

	private Image frame;
	private boolean cameraStarted;
	private USBCamera camera;

	public MicrosoftLifeCam(String cameraName) {
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
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

}
