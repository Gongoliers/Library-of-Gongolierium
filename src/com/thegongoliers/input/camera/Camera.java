package com.thegongoliers.input.camera;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Range;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.thegongoliers.geometry.Point;

/**
 * Allows for abstract use of the camera.
 * 
 * @author Kyle
 *
 */
public class Camera {

	private CameraInterface camera;
	private int targetExposure, targetBrightness, normalBrightness;
	private Mode cameraMode;
	private HashMap<String, TargetSpecifications> defaultTargets;

	Camera(CameraInterface camera, int targetExposure, int targetBrightness, int normalBrightness,
			HashMap<String, TargetSpecifications> targets) {
		this.camera = camera;
		this.targetBrightness = targetBrightness;
		this.targetExposure = targetExposure;
		this.normalBrightness = normalBrightness;
		defaultTargets = targets;
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

	public TargetReport findTarget(String name, double minPercentArea) throws TargetNotFoundException {
		return findTarget(defaultTargets.get(name), minPercentArea);
	}

	/**
	 * This method locates the largest object in the cameras view that is within
	 * the custom HSV range. This will return a Target object which contains
	 * details about the target including its center location, distance, and
	 * angle to the camera.
	 * 
	 * @param targetSpecs
	 *            The specifications of the target
	 * @param minPercentArea
	 *            The minimum percent area of the target
	 * @return The target information.
	 */
	public TargetReport findTarget(TargetSpecifications targetSpecs, double minPercentArea)
			throws TargetNotFoundException {
		Mat binaryFilteredImage = filterRetroreflective(targetSpecs.getHue(), targetSpecs.getSaturation(),
				targetSpecs.getValue());
		ParticleReport particleReport = generateParticleReport(binaryFilteredImage, findBlob(binaryFilteredImage));
		if (particleReport == null || particleReport.percentAreaToImageArea < minPercentArea)
			throw new TargetNotFoundException();
		double rawX = (particleReport.boundingRectLeft + particleReport.boundingRectRight) / 2;
		double rawY = (particleReport.boundingRectBottom + particleReport.boundingRectTop) / 2;
		double angle = 90 - computeAngle(binaryFilteredImage, rawX);
		double distance = computeDistance(binaryFilteredImage, particleReport, targetSpecs.getWidth());
		Point aimingCoordinates = toAimingCoordinates(new Point(rawX, rawY, 0));
		double aspectRatio = (particleReport.boundingRect.width / particleReport.boundingRect.height);
		double aspectScore = Scorer.score(aspectRatio, targetSpecs.getWidth() / targetSpecs.getHeight());

		double areaRatio = particleReport.blobArea / particleReport.boundingRect.area();
		double areaScore = Scorer.score(areaRatio,
				targetSpecs.getArea() / (targetSpecs.getHeight() * targetSpecs.getWidth()));

		int confidence = (int) Math.round((aspectScore + areaScore) / 2);

		TargetReport target = new TargetReport(confidence, angle, distance, aimingCoordinates);

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

	private Mat filterRetroreflective(Range hue, Range saturation, Range value) {
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

	public static class TargetReport implements TargetReportInterface {

		private int confidence;
		private double angle, distance;
		private Point aimingCoordinates;

		public TargetReport(int confidence, double angle, double distance, Point aimingCoordinates) {
			this.confidence = confidence;
			this.angle = angle;
			this.distance = distance;
			this.aimingCoordinates = aimingCoordinates;
		}

		@Override
		public int confidence() {
			return confidence;
		}

		@Override
		public double angle() {
			return angle;
		}

		@Override
		public double distance() {
			return distance;
		}

		@Override
		public Point aimingCoordinates() {
			return aimingCoordinates;
		}

	}

	public static class Builder {
		private CameraInterface camera;
		private int targetExposure, targetBrightness, normalBrightness = 50;
		private HashMap<String, TargetSpecifications> targets;

		public Builder(CameraInterface camera) {
			this.camera = camera;
			this.targets = new HashMap<>();
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
		public Builder setTargetExposure(int exposure) {
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
		public Builder setTargetBrightness(int brightness) {
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
		public Builder setNormalBrightness(int brightness) {
			normalBrightness = brightness;
			return this;
		}

		public Builder addTargetSpecification(String name, TargetSpecifications target) {
			targets.put(name, target);
			return this;
		}

		/**
		 * This method takes all of the specified settings and generates a
		 * Camera object.
		 * 
		 * @return The camera object.
		 */
		public Camera build() {
			if (camera == null)
				throw new RuntimeException("Camera can not be null");
			return new Camera(camera, targetExposure, targetBrightness, normalBrightness, targets);
		}
	}

}
