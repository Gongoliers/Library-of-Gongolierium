package com.thegongoliers.input.camera;

import org.opencv.core.Mat;

import edu.wpi.cscore.VideoSource;

public interface CameraInterface {
	/**
	 * Get the view angle of the camera in degrees.
	 * 
	 * @return The view angle in degrees.
	 */
	double getViewAngle();

	/**
	 * Set the brightness of the camera image.
	 * 
	 * @param brightness
	 *            The brightness (0, 100).
	 */
	void setBrightness(int brightness);

	/**
	 * Get the current brightness of the camera image.
	 * 
	 * @return The brightness.
	 */
	int getBrightness();

	/**
	 * Set the exposure of the camera.
	 * 
	 * @param exposure
	 *            The exposure (0, 100).
	 */
	void setExposureManual(int exposure);

	/**
	 * Set the exposure of the camera to auto.
	 */
	void setExposureAuto();

	/**
	 * Get the current image from the camera.
	 * 
	 * @return The current image.
	 */
	Mat getImage();

	/**
	 * Start the camera.
	 */
	void start();

	/**
	 * Stop the camera.
	 */
	void stop();

	/**
	 * Set the frames per second of the camera.
	 * 
	 * @param fps
	 *            The FPS of the camera.
	 */
	void setFPS(int fps);

	/**
	 * Get the resolution of the image.
	 * 
	 * @param axis
	 *            The Axis to get the resolution.
	 * @return The resolution of the axis in pixels.
	 */
	int getResolution(Axis axis);

	/**
	 * Gets the video source of this camera.
	 * 
	 * @return The VideoSource of the camera.
	 */
	VideoSource getVideoSource();

	/**
	 * Display the camera VideoSource
	 */
	void display();

}
