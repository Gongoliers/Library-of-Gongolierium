package com.thegongoliers.input.camera;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Range;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.thegongoliers.geometry.Cylindrical;
import com.thegongoliers.geometry.Point;
import com.thegongoliers.geometry.Pose;
import com.thegongoliers.math.MathExt;
import com.thegongoliers.math.TF;

/**
 * Allows for abstract use of the camera.
 * 
 * @author Kyle
 *
 */
public class Camera {

	private CameraInterface camera;
	private int targetExposure, targetBrightness, normalBrightness;
	private Range hue, saturation, value;
	private Mode cameraMode;

	Camera(CameraInterface camera, int targetExposure, int targetBrightness, int normalBrightness, Range hue,
			Range saturation, Range value) {
		this.camera = camera;
		this.targetBrightness = targetBrightness;
		this.targetExposure = targetExposure;
		this.normalBrightness = normalBrightness;
		this.hue = hue;
		this.saturation = saturation;
		this.value = value;
		camera.start();
		disableTargetMode();
	}

	/**
	 * This method returns the current image from the camera.
	 * 
	 * @return The current frame.
	 */
	public Mat getImage() {
		return camera.getImage();
	}

	/**
	 * This method displays the current video stream onto the SmartDashboard.
	 */
	public void display() {
		camera.display();
	}

	/**
	 * This method sets the exposure level of the camera. The parameter exposure
	 * is on the scale of 0 to 100.
	 * 
	 * @param exposure
	 *            The exposure of the image.
	 */
	public void setExposure(int exposure) {
		camera.setExposureManual(exposure);
	}

	/**
	 * This method returns the cameras exposure level to auto.
	 */
	public void setAutoExposure() {
		camera.setExposureAuto();
	}

	/**
	 * This method sets the brightness level of the image. The parameter
	 * brightness is on the scale of 0 to 100.
	 * 
	 * @param brightness
	 *            The brightness of the image.
	 */
	public void setBrightness(int brightness) {
		camera.setBrightness(brightness);
	}

	/**
	 * This method enables the target mode of the camera to allow it to see the
	 * retroreflection target. (exposure and brightness were set in
	 * CameraBuilder default=0).
	 */
	public void enableTargetMode() {
		setBrightness(targetBrightness);
		setExposure(targetExposure);
		cameraMode = Mode.TARGET;
	}

	/**
	 * This method disables the target mode of the camera to allow it to take
	 * normal pictures (exposure auto, brightness was specified in CameraBuilder
	 * default=50).
	 */
	public void disableTargetMode() {
		setBrightness(normalBrightness);
		setAutoExposure();
		cameraMode = Mode.NORMAL;
	}

	/**
	 * This method returns the current mode of the camera. It returns an enum,
	 * Camera.Mode which can either be TARGET or NORMAL.
	 * 
	 * @return The mode of the image.
	 */
	public Mode getMode() {
		return cameraMode;
	}

	/**
	 * This method locates the largest object in the cameras view that is within
	 * the custom HSV range. This will return a Target object which contains
	 * details about the target including its center location, distance, and
	 * angle to the camera.
	 * 
	 * @param width
	 *            The width of the actual target.
	 * @param height
	 *            The height of the actual target.
	 * @return The target information.
	 */
	public Target findTarget(double width, double height) throws TargetNotFoundException {
		Mat binaryFilteredImage = filterRetroreflective();
		ParticleReport particleReport = generateParticleReport(binaryFilteredImage, findBlob(binaryFilteredImage));
		if (particleReport == null)
			throw new TargetNotFoundException();
		double rawX = (particleReport.boundingRectLeft + particleReport.boundingRectRight) / 2;
		double rawY = (particleReport.boundingRectBottom + particleReport.boundingRectTop) / 2;
		Target target = new Target();
		target.boundingRectangle = particleReport.boundingRect;
		target.distance = computeDistance(binaryFilteredImage, particleReport, width);
		target.centerX = rawX;
		target.centerY = rawY;
		target.aimingCoordinates = toAimingCoordinates(new Point(rawX, rawY, 0));
		target.angle = computeAngle(binaryFilteredImage, rawX);
		target.alpha = 90 - camera.getViewAngle();
		target.width = Math.abs(particleReport.boundingRectRight - particleReport.boundingRectLeft);
		target.height = Math.abs(particleReport.boundingRectBottom - particleReport.boundingRectTop);
		target.percentArea = particleReport.percentAreaToImageArea;
		target.targetArea = particleReport.blobArea;
		return target;
	}

	private Point toAimingCoordinates(Point pixels) {
		double aimingX = (pixels.x - camera.getResolution(Axis.X) / 2.0) / (camera.getResolution(Axis.X) / 2.0);
		double aimingY = (pixels.y - camera.getResolution(Axis.Y) / 2.0) / (camera.getResolution(Axis.Y) / 2.0);
		return new Point(aimingX, aimingY, 0);
	}

	private double computeDistance(Mat image, ParticleReport report, double width) {
		double normalizedWidth;
		Size size = image.size();
		normalizedWidth = 2 * (report.boundingRectRight - report.boundingRectLeft) / size.width;
		return width / (normalizedWidth * Math.tan(Math.toRadians(camera.getViewAngle() / 2)));
	}

	private double computeAngle(Mat image, double centerX) {
		Size size = image.size();
		double aimingCoordinate = (centerX / size.width) * 2 - 1;
		return aimingCoordinate * camera.getViewAngle() / 2;
	}

	private Mat filterRetroreflective() {
		Mat rawCameraImage = getImage();
		Mat hsvCameraImage = new Mat();
		Imgproc.cvtColor(rawCameraImage, hsvCameraImage, Imgproc.COLOR_BGR2HSV);
		Scalar minRange = new Scalar(hue.start, saturation.start, value.start);
		Scalar maxRange = new Scalar(hue.end, saturation.end, value.end);
		Mat binaryImage = new Mat();
		Core.inRange(hsvCameraImage, minRange, maxRange, binaryImage);
		return binaryImage;
	}

	private Rect findBlob(Mat binaryFilteredImage) {
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Mat hierarchy = new Mat();
		Imgproc.findContours(binaryFilteredImage, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		double largest = 0;
		int largestIdx = 0;
		if (contours.isEmpty())
			return null;
		for (int idx = 0; idx < contours.size(); idx++) {
			double area = Imgproc.boundingRect(contours.get(idx)).area();
			if (area > largest) {
				largest = area;
				largestIdx = idx;
			}
		}
		Rect bounding = Imgproc.boundingRect(contours.get(largestIdx));
		return bounding;
	}

	private ParticleReport generateParticleReport(Mat binaryImage, Rect boundingRect) {
		ParticleReport report = new ParticleReport();
		if (boundingRect == null)
			return null;
		report.area = boundingRect.area();
		int sum = 0;
		for (int i = (int) boundingRect.tl().x; i < boundingRect.br().x; i++) {
			for (int j = (int) boundingRect.tl().y; j < boundingRect.br().y; j++) {
				double[] vals = binaryImage.get(j, i);
				int s = 0;
				for (double val : vals) {
					s += val;
				}
				if (s > 0) {
					sum++;
				}
			}
		}
		report.boundingRect = boundingRect;
		report.blobArea = sum;
		report.percentAreaToImageArea = report.area / binaryImage.size().area() * 100;
		report.boundingRectBottom = boundingRect.br().y;
		report.boundingRectLeft = boundingRect.tl().x;
		report.boundingRectRight = boundingRect.br().x;
		report.boundingRectTop = boundingRect.tl().y;
		return report;
	}

	private class ParticleReport implements Comparator<ParticleReport>, Comparable<ParticleReport> {
		double percentAreaToImageArea;
		double blobArea;
		double area;
		double boundingRectLeft;
		double boundingRectTop;
		double boundingRectRight;
		double boundingRectBottom;
		Rect boundingRect;

		public int compareTo(ParticleReport r) {
			return (int) (r.area - this.area);
		}

		public int compare(ParticleReport r1, ParticleReport r2) {
			return (int) (r1.area - r2.area);
		}
	};

	public static enum Mode {
		TARGET, NORMAL
	}

	public static enum LEDColor {
		GREEN
	}

	public static class Target {
		private double centerX, centerY;
		private double alpha;
		private double distance;
		private double angle;
		private double width;
		private double height;
		private double percentArea;
		private double targetArea;
		private Rect boundingRectangle;
		private Point aimingCoordinates;

		/**
		 * The aspect ratio of the target bounding rectangle. A good score
		 * should approach the width / height of the actual bounding rectangle
		 * of your target.
		 * 
		 * @return The width / height of the target bounding rectangle.
		 */
		public double getAspectRatio() {
			if (getHeight() == 0) {
				return 0;
			}
			return getWidth() / getHeight();
		}

		/**
		 * The coverage area of the target. A good score should approach the
		 * coverage area of your target as the following ratio (area
		 * target)/(area bounding rectangle).
		 * 
		 * @return The area of the target divided by the area of the bounding
		 *         rectangle.
		 */
		public double getCoverageArea() {
			if (getBoundingRectangle() == null || getBoundingRectangle().area() == 0) {
				return 0;
			}
			return getTargetArea() / getBoundingRectangle().area();
		}

		/**
		 * Calculates the position of the target from the robot's frame of
		 * reference
		 * 
		 * @param offset
		 *            The camera's offset from the center (in the same units as
		 *            the target dimensions)
		 * @return The Point in space of of the target from the center of the
		 *         robot.
		 */
		public Point toRobotFrame(Pose offset) {
			Point cartesian = MathExt.toCartesian(new Cylindrical(distance, alpha, 0));
			TF tf = new TF();
			tf.put("camera", offset);
			return tf.transformToOrigin(cartesian, "camera").position;
		}

		public Point getAimingCoordinates() {
			return aimingCoordinates;
		}

		public Rect getBoundingRectangle() {
			return boundingRectangle;
		}

		public double getPercentArea() {
			return percentArea;
		}

		public double getTargetArea() {
			return targetArea;
		}

		public double getWidth() {
			return width;
		}

		public double getHeight() {
			return height;
		}

		public double getCenterX() {
			return centerX;
		}

		public double getCenterY() {
			return centerY;
		}

		public double getDistance() {
			return distance;
		}

		public double getAngle() {
			return angle;
		}
	}

	public static class CameraBuilder {
		private CameraInterface camera;
		private int targetExposure, targetBrightness, normalBrightness = 50;
		private Range hue, saturation, value;

		/**
		 * This method sets the HSV range of the findTarget method of the Camera
		 * class based on the LED color specified. Currently, only GREEN is
		 * supported.
		 * 
		 * @param color
		 *            The color of the led.
		 * @return The CameraBuilder object
		 */
		public CameraBuilder setLEDColor(LEDColor color) {
			switch (color) {
			case GREEN:
				hue = new Range(75, 125);
				saturation = new Range(175, 255);
				value = new Range(65, 255);
				break;
			}
			return this;
		}

		/**
		 * This method sets the type of camera being used. Currently, only the
		 * MicrosoftLifeCam class implements CameraInterface.
		 * 
		 * @param camera
		 *            The camera.
		 * @return The CameraBuilder object
		 */
		public CameraBuilder setCamera(CameraInterface camera) {
			this.camera = camera;
			return this;
		}

		/**
		 * This sets the exposure of the camera during target mode. The
		 * parameter exposure is on the scale 0 to 100. The default for this is
		 * 0.
		 * 
		 * @param exposure
		 *            The target exposure.
		 * @return The CameraBuilder object
		 */
		public CameraBuilder setTargetExposure(int exposure) {
			targetExposure = exposure;
			return this;
		}

		/**
		 * This sets the brightness of the camera during target mode. The
		 * parameter brightness is on the scale 0 to 100. The default for this
		 * is 0.
		 * 
		 * @param brightness
		 *            The target brightness.
		 * @return The CameraBuilder object
		 */
		public CameraBuilder setTargetBrightness(int brightness) {
			targetBrightness = brightness;
			return this;
		}

		/**
		 * This sets the default brightness of the camera when it is not in
		 * target mode. The parameter brightness is on the scale 0 to 100. The
		 * default for this is 50.
		 * 
		 * @param brightness
		 *            The normal brightness.
		 * @return The CameraBuilder object
		 */
		public CameraBuilder setNormalBrightness(int brightness) {
			normalBrightness = brightness;
			return this;
		}

		/**
		 * This method sets the HSV range of the findTarget method of the Camera
		 * class. You do not have to use this method, using setLEDColor will
		 * prefill the HSV values. The default color is the same as
		 * setLEDColor(LEDColor.GREEN).
		 * 
		 * @param hue
		 *            The hue range.
		 * @param saturation
		 *            The saturation range.
		 * @param value
		 *            The value range.
		 * @return The CameraBuilder object
		 */
		public CameraBuilder setManualHSVRange(Range hue, Range saturation, Range value) {
			this.hue = hue;
			this.saturation = saturation;
			this.value = value;
			return this;
		}

		/**
		 * This method takes all of the specified settings and generates a
		 * Camera object.
		 * 
		 * @return The camera object.
		 */
		public Camera build() {
			if (hue == null)
				this.setLEDColor(LEDColor.GREEN);
			if (camera == null)
				throw new RuntimeException("Camera can not be null");
			return new Camera(camera, targetExposure, targetBrightness, normalBrightness, hue, saturation, value);
		}
	}

}
