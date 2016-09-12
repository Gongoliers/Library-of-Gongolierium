package com.thegongoliers.input;

import java.util.Comparator;
import java.util.Vector;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.thegongoliers.input.CameraInterface.Axis;
import com.thegongoliers.util.Position;

/**
 * Allows for abstract use of the camera.
 * 
 * @author Kyle
 *
 */
public class Camera {

	private CameraInterface camera;
	private int targetExposure, targetBrightness, normalBrightness;
	private NIVision.Range hue, saturation, value;
	private Mode cameraMode;

	Camera(CameraInterface camera, int targetExposure, int targetBrightness, int normalBrightness, NIVision.Range hue,
			NIVision.Range saturation, NIVision.Range value) {
		this.camera = camera;
		this.targetBrightness = targetBrightness;
		this.targetExposure = targetExposure;
		this.normalBrightness = normalBrightness;
		this.hue = hue;
		this.saturation = saturation;
		this.value = value;
		disableTargetMode();
	}

	/**
	 * This method returns the current image from the camera.
	 * 
	 * @return The current frame.
	 */
	public Image getImage() {
		return camera.getImage();
	}

	/**
	 * This method displays the current image onto the SmartDashboard. Call this
	 * method repeatedly to have a live stream of images.
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
	public Target findTarget(double width, double height) {
		Image binaryFilteredImage = filterRetroreflective();
		ParticleReport particleReport = generateParticleReport(binaryFilteredImage);
		if (particleReport == null)
			return new Target();
		double rawX = (particleReport.BoundingRectLeft + particleReport.BoundingRectRight) / 2;
		double rawY = (particleReport.BoundingRectBottom + particleReport.BoundingRectTop) / 2;
		Target target = new Target();
		target.distance = computeDistance(binaryFilteredImage, particleReport, width);
		target.centerX = rawX;
		target.centerY = rawY;
		target.aimingCoordinates = toAimingCoordinates(new Position(rawX, rawY));
		target.angle = computeAngle(binaryFilteredImage, rawX);
		target.width = Math.abs(particleReport.BoundingRectRight - particleReport.BoundingRectLeft);
		target.height = Math.abs(particleReport.BoundingRectBottom - particleReport.BoundingRectTop);
		target.percentArea = particleReport.PercentAreaToImageArea;
		target.experimentalDistance = computeRobotDistance(target.distance, rawX);
		target.experimentalAngle = computeRobotAngle(binaryFilteredImage, rawX, target.experimentalDistance);
		return target;
	}

	private Position toAimingCoordinates(Position pixels) {
		double aimingX = (pixels.getX() - camera.getResolution(Axis.X) / 2.0) / (camera.getResolution(Axis.X) / 2.0);
		double aimingY = (pixels.getY() - camera.getResolution(Axis.Y) / 2.0) / (camera.getResolution(Axis.Y) / 2.0);
		return new Position(aimingX, aimingY);
	}

	private double computeDistance(Image image, ParticleReport report, double width) {
		double normalizedWidth;
		NIVision.GetImageSizeResult size;

		size = NIVision.imaqGetImageSize(image);
		normalizedWidth = 2 * (report.BoundingRectRight - report.BoundingRectLeft) / size.width;
		return width / (normalizedWidth * Math.tan(Math.toRadians(camera.getViewAngle() / 2)));
	}

	private double computeAngle(Image image, double centerX) {
		NIVision.GetImageSizeResult size = NIVision.imaqGetImageSize(image);
		double aimingCoordinate = (centerX / size.width) * 2 - 1;
		return aimingCoordinate * camera.getViewAngle() / 2;
	}

	/**
	 * @author FRC Team #125, NUtrons
	 * 
	 */
	private double computeRobotDistance(double cameraDistance, double cameraXAngle) {
		double angle = 90 + Math.abs(cameraXAngle);
		double cameraOffset = camera.getHorizontalOffset();
		if (cameraOffset == 0.0) {
			return cameraDistance;
		}

		return Math.sqrt(Math.pow(cameraOffset, 2) + Math.pow(cameraDistance, 2)
				- (2 * Math.abs(cameraOffset) * cameraDistance * Math.cos(Math.toRadians(angle))));

	}

	/**
	 * @author FRC Team #125, NUtrons
	 * 
	 */
	private double computeRobotAngle(Image image, double centerX, double cameraDistance) {
		double distance = computeRobotDistance(cameraDistance, computeAngle(image, centerX));
		return Math.toDegrees(Math
				.acos(Math.pow(camera.getHorizontalOffset(), 2) - Math.pow(cameraDistance, 2) + Math.pow(distance, 2))
				/ (2 * distance * Math.abs(camera.getHorizontalOffset()))) - 90.0;
	}

	private Image filterRetroreflective() {
		Image binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		NIVision.imaqColorThreshold(binaryFrame, getImage(), 255, NIVision.ColorMode.HSV, hue, saturation, value);
		return binaryFrame;
	}

	private ParticleReport generateParticleReport(Image binaryFilteredImage) {
		int numParticles = NIVision.imaqCountParticles(binaryFilteredImage, 1);
		if (numParticles > 0) {
			Vector<ParticleReport> particles = new Vector<ParticleReport>();
			for (int particleIndex = 0; particleIndex < numParticles; particleIndex++) {
				ParticleReport par = new ParticleReport();
				par.PercentAreaToImageArea = NIVision.imaqMeasureParticle(binaryFilteredImage, particleIndex, 0,
						NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
				par.Area = NIVision.imaqMeasureParticle(binaryFilteredImage, particleIndex, 0,
						NIVision.MeasurementType.MT_AREA);
				par.BoundingRectTop = NIVision.imaqMeasureParticle(binaryFilteredImage, particleIndex, 0,
						NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
				par.BoundingRectLeft = NIVision.imaqMeasureParticle(binaryFilteredImage, particleIndex, 0,
						NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
				par.BoundingRectBottom = NIVision.imaqMeasureParticle(binaryFilteredImage, particleIndex, 0,
						NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
				par.BoundingRectRight = NIVision.imaqMeasureParticle(binaryFilteredImage, particleIndex, 0,
						NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
				particles.add(par);
			}
			particles.sort(null);
			return particles.elementAt(0);
		}
		return null;
	}

	private class ParticleReport implements Comparator<ParticleReport>, Comparable<ParticleReport> {
		double PercentAreaToImageArea;
		double Area;
		double BoundingRectLeft;
		double BoundingRectTop;
		double BoundingRectRight;
		double BoundingRectBottom;

		public int compareTo(ParticleReport r) {
			return (int) (r.Area - this.Area);
		}

		public int compare(ParticleReport r1, ParticleReport r2) {
			return (int) (r1.Area - r2.Area);
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
		private double distance;
		private double angle;
		private double width;
		private double height;
		private double percentArea;
		private double experimentalDistance;
		private double experimentalAngle;
		private Position aimingCoordinates;

		public Position getAimingCoordinates() {
			return aimingCoordinates;
		}

		public double getExperimentalDistance() {
			return experimentalDistance;
		}

		public double getExperimentalAngle() {
			return experimentalAngle;
		}

		public double getPercentArea() {
			return percentArea;
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
		private NIVision.Range hue, saturation, value;

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
				hue = new NIVision.Range(75, 125);
				saturation = new NIVision.Range(175, 255);
				value = new NIVision.Range(65, 255);
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
		public CameraBuilder setManualHSVRange(NIVision.Range hue, NIVision.Range saturation, NIVision.Range value) {
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
				this.setCamera(new MicrosoftLifeCam("cam0"));
			return new Camera(camera, targetExposure, targetBrightness, normalBrightness, hue, saturation, value);
		}
	}

}
