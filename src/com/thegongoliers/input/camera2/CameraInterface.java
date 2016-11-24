package com.thegongoliers.input.camera2;
import org.opencv.core.Mat;

public interface CameraInterface {

	public static enum Axis {
		X, Y
	}

	/**
	 * Get the view angle of the camera in degrees.
	 * 
	 * @return The view angle in degres.
	 */
	public double getViewAngle();

	/**
	 * Set the brightness of the camera image.
	 * 
	 * @param brightness
	 *            The brightness (0, 100).
	 */
	public void setBrightness(int brightness);

	/**
	 * Get the current brightness of the camera image.
	 * 
	 * @return The brightness.
	 */
	public int getBrightness();

	/**
	 * Set the exposure of the camera.
	 * 
	 * @param exposure
	 *            The exposure (0, 100).
	 */
	public void setExposureManual(int exposure);

	/**
	 * Set the exposure of the camera to auto.
	 */
	public void setExposureAuto();

	/**
	 * Get the current image from the camera.
	 * 
	 * @return The current image.
	 */
	public Mat getImage();

	/**
	 * Start the camera.
	 */
	public void start();

	/**
	 * Stop the camera.
	 */
	public void stop();

	/**
	 * Set the frames per second of the camera.
	 * 
	 * @param fps
	 *            The FPS of the camera.
	 */
	public void setFPS(int fps);

	/**
	 * Display the camera image on the SmartDashboard.
	 */
	public void display();

	/**
	 * Get the resolution of the image.
	 * 
	 * @param axis
	 *            The Axis to get the resolution.
	 * @return The resolution of the axis in pixels.
	 */
	public int getResolution(Axis axis);

	/**
	 * Get the horizontal offset of the camera from the center of the robot.
	 * 
	 * @return The horizontal offset of the camera.
	 */
	public double getHorizontalOffset();

	/**
	 * Set the horizontal offset of the camera from the center of the robot.
	 * 
	 * @param hOffset
	 *            The horizontal offset of the camera.
	 */
	public void setHorizontalOffset(double hOffset);

}
