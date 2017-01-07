package com.thegongoliers.input.camera;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.thegongoliers.geometry.Point;

import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.CameraServer;

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
	private CvSource outputStream;
	private boolean drawRect;

	Camera(CameraInterface camera, int targetExposure, int targetBrightness, int normalBrightness,
			HashMap<String, TargetSpecifications> targets, boolean drawRect) {
		this.camera = camera;
		this.targetBrightness = targetBrightness;
		this.targetExposure = targetExposure;
		this.normalBrightness = normalBrightness;
		defaultTargets = targets;
		camera.start();
		this.drawRect = drawRect;
		if (drawRect)
			outputStream = CameraServer.getInstance().putVideo("Target", camera.getResolution(Axis.X),
					camera.getResolution(Axis.Y));
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

	public TargetReport findTarget(String name, double minArea) throws TargetNotFoundException {
		return findTarget(defaultTargets.get(name), minArea);
	}

	public void findTargetAsync(String name, double minArea, Consumer<TargetReport> processResult) {
		new Thread(() -> {
			try {
				TargetReport target = findTarget(name, minArea);
				processResult.accept(target);
			} catch (TargetNotFoundException e) {
				processResult.accept(null);
			}
		}).start();
	}

	public void findTargetAsync(TargetSpecifications specs, double minArea, Consumer<TargetReport> processResult) {
		new Thread(() -> {
			try {
				TargetReport target = findTarget(specs, minArea);
				processResult.accept(target);
			} catch (TargetNotFoundException e) {
				processResult.accept(null);
			}
		}).start();
	}

	public Thread continuouslyFindTargetAsync(String name, double minArea, Consumer<TargetReport> processResult) {
		return new Thread(() -> {
			while (true) {
				try {
					TargetReport target = findTarget(name, minArea);
					processResult.accept(target);
				} catch (TargetNotFoundException e) {
					processResult.accept(null);
				}
			}
		});
	}

	public Thread continuouslyFindTargetAsync(TargetSpecifications specs, double minArea,
			Consumer<TargetReport> processResult) {
		return new Thread(() -> {
			while (true) {
				try {
					TargetReport target = findTarget(specs, minArea);
					processResult.accept(target);
				} catch (TargetNotFoundException e) {
					processResult.accept(null);
				}
			}
		});
	}

	/**
	 * This method locates the largest object in the cameras view that is within
	 * the custom HSV range. This will return a Target object which contains
	 * details about the target including its center location, distance, and
	 * angle to the camera.
	 * 
	 * @param targetSpecs
	 *            The specifications of the target
	 * @param minArea
	 *            The minimum area in pixels of the target
	 * @return The target information.
	 */
	public TargetReport findTarget(TargetSpecifications targetSpecs, double minArea) throws TargetNotFoundException {
		double[] hue = { targetSpecs.getHue().start, targetSpecs.getHue().end };
		double[] sat = { targetSpecs.getSaturation().start, targetSpecs.getSaturation().end };
		double[] val = { targetSpecs.getValue().start, targetSpecs.getValue().end };
		Pipeline p = new Pipeline();
		Mat image = getImage();
		p.source0 = image;
		p.process(hue, sat, val, minArea);
		List<MatOfPoint> contours = p.filterContoursOutput();
		if (contours.isEmpty())
			throw new TargetNotFoundException();
		MatOfPoint contour = contours.get(0);
		Rect boundary = Imgproc.boundingRect(contour);
		double rawX = boundary.x + boundary.width / 2;
		double rawY = boundary.y + boundary.height / 2;
		double angle = 90 - computeAngle(p.hsvThresholdOutput(), rawX);
		double distance = computeDistance(p.hsvThresholdOutput(), boundary.width, targetSpecs.getWidth());
		Point aimingCoordinates = toAimingCoordinates(new Point(rawX, rawY, 0));
		double aspectRatio = (boundary.width / boundary.height);
		double aspectScore = Scorer.score(aspectRatio, targetSpecs.getWidth() / targetSpecs.getHeight());

		double areaRatio = Imgproc.contourArea(contour) / boundary.area();
		double areaScore = Scorer.score(areaRatio,
				targetSpecs.getArea() / (targetSpecs.getHeight() * targetSpecs.getWidth()));

		int confidence = (int) Math.round((aspectScore + areaScore) / 2);

		TargetReport target = new TargetReport(confidence, angle, distance, aimingCoordinates);

		if (drawRect && outputStream != null) {
			ImageEditor.drawRectangleToMat(image, boundary, new Scalar(255, 0, 0));
			outputStream.putFrame(image);
		}

		return target;
	}

	private Point toAimingCoordinates(Point pixels) {
		double aimingX = (pixels.x - camera.getResolution(Axis.X) / 2.0) / (camera.getResolution(Axis.X) / 2.0);
		double aimingY = (pixels.y - camera.getResolution(Axis.Y) / 2.0) / (camera.getResolution(Axis.Y) / 2.0);
		return new Point(aimingX, aimingY, 0);
	}

	private double computeDistance(Mat image, double targetWidth, double width) {
		double normalizedWidth;
		Size size = image.size();
		normalizedWidth = 2 * targetWidth / size.width;
		return width / (normalizedWidth * Math.tan(Math.toRadians(camera.getViewAngle() / 2)));
	}

	private double computeAngle(Mat image, double centerX) {
		Size size = image.size();
		double aimingCoordinate = (centerX / size.width) * 2 - 1;
		return aimingCoordinate * camera.getViewAngle() / 2;
	}

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

		@Override
		public String toString() {
			return "<Target angle=" + angle() + " distance=" + distance() + " confidence=" + confidence() + "% >";
		}

	}

	public static class Builder {
		private CameraInterface camera;
		private boolean drawRect;
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

		public Builder shouldOutlineTarget(boolean drawRect) {
			this.drawRect = drawRect;
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
			return new Camera(camera, targetExposure, targetBrightness, normalBrightness, targets, drawRect);
		}
	}

	class Pipeline {

		// Outputs
		private Mat hsvThresholdOutput = new Mat();
		private Mat cvErodeOutput = new Mat();
		private ArrayList<MatOfPoint> findContoursOutput = new ArrayList<MatOfPoint>();
		private ArrayList<MatOfPoint> filterContoursOutput = new ArrayList<MatOfPoint>();

		// Sources
		private Mat source0;

		/**
		 * This constructor sets up the pipeline
		 */
		public Pipeline() {
		}

		/**
		 * This is the primary method that runs the entire pipeline and updates
		 * the outputs.
		 */
		public void process(double[] hsvThresholdHue, double[] hsvThresholdSaturation, double[] hsvThresholdValue,
				double filterContoursMinArea) {
			// Step HSV_Threshold0:
			Mat hsvThresholdInput = source0;
			hsvThreshold(hsvThresholdInput, hsvThresholdHue, hsvThresholdSaturation, hsvThresholdValue,
					hsvThresholdOutput);

			// Step CV_erode0:
			Mat cvErodeSrc = hsvThresholdOutput;
			Mat cvErodeKernel = new Mat();
			org.opencv.core.Point cvErodeAnchor = new org.opencv.core.Point(-1, -1);
			double cvErodeIterations = 1.0;
			int cvErodeBordertype = Core.BORDER_CONSTANT;
			Scalar cvErodeBordervalue = new Scalar(-1);
			cvErode(cvErodeSrc, cvErodeKernel, cvErodeAnchor, cvErodeIterations, cvErodeBordertype, cvErodeBordervalue,
					cvErodeOutput);

			// Step Find_Contours0:
			Mat findContoursInput = cvErodeOutput;
			boolean findContoursExternalOnly = false;
			findContours(findContoursInput, findContoursExternalOnly, findContoursOutput);

			// Step Filter_Contours0:
			ArrayList<MatOfPoint> filterContoursContours = findContoursOutput;
			double filterContoursMinPerimeter = 0;
			double filterContoursMinWidth = 0;
			double filterContoursMaxWidth = 1000;
			double filterContoursMinHeight = 0;
			double filterContoursMaxHeight = 1000;
			double[] filterContoursSolidity = { 0, 100 };
			double filterContoursMaxVertices = 1000000;
			double filterContoursMinVertices = 0;
			double filterContoursMinRatio = 0;
			double filterContoursMaxRatio = 1000;
			filterContours(filterContoursContours, filterContoursMinArea, filterContoursMinPerimeter,
					filterContoursMinWidth, filterContoursMaxWidth, filterContoursMinHeight, filterContoursMaxHeight,
					filterContoursSolidity, filterContoursMaxVertices, filterContoursMinVertices,
					filterContoursMinRatio, filterContoursMaxRatio, filterContoursOutput);

		}

		/**
		 * This method is a generated setter for source0.
		 * 
		 * @param source
		 *            the Mat to set
		 */
		public void setsource0(Mat source0) {
			this.source0 = source0;
		}

		/**
		 * This method is a generated getter for the output of a HSV_Threshold.
		 * 
		 * @return Mat output from HSV_Threshold.
		 */
		public Mat hsvThresholdOutput() {
			return hsvThresholdOutput;
		}

		/**
		 * This method is a generated getter for the output of a CV_erode.
		 * 
		 * @return Mat output from CV_erode.
		 */
		public Mat cvErodeOutput() {
			return cvErodeOutput;
		}

		/**
		 * This method is a generated getter for the output of a Find_Contours.
		 * 
		 * @return ArrayList<MatOfPoint> output from Find_Contours.
		 */
		public ArrayList<MatOfPoint> findContoursOutput() {
			return findContoursOutput;
		}

		/**
		 * This method is a generated getter for the output of a
		 * Filter_Contours.
		 * 
		 * @return ArrayList<MatOfPoint> output from Filter_Contours.
		 */
		public ArrayList<MatOfPoint> filterContoursOutput() {
			return filterContoursOutput;
		}

		/**
		 * Segment an image based on hue, saturation, and value ranges.
		 *
		 * @param input
		 *            The image on which to perform the HSL threshold.
		 * @param hue
		 *            The min and max hue
		 * @param sat
		 *            The min and max saturation
		 * @param val
		 *            The min and max value
		 * @param output
		 *            The image in which to store the output.
		 */
		private void hsvThreshold(Mat input, double[] hue, double[] sat, double[] val, Mat out) {
			Imgproc.cvtColor(input, out, Imgproc.COLOR_BGR2HSV);
			Core.inRange(out, new Scalar(hue[0], sat[0], val[0]), new Scalar(hue[1], sat[1], val[1]), out);
		}

		/**
		 * Expands area of lower value in an image.
		 * 
		 * @param src
		 *            the Image to erode.
		 * @param kernel
		 *            the kernel for erosion.
		 * @param anchor
		 *            the center of the kernel.
		 * @param iterations
		 *            the number of times to perform the erosion.
		 * @param borderType
		 *            pixel extrapolation method.
		 * @param borderValue
		 *            value to be used for a constant border.
		 * @param dst
		 *            Output Image.
		 */
		private void cvErode(Mat src, Mat kernel, org.opencv.core.Point anchor, double iterations, int borderType,
				Scalar borderValue, Mat dst) {
			if (kernel == null) {
				kernel = new Mat();
			}
			if (anchor == null) {
				anchor = new org.opencv.core.Point(-1, -1);
			}
			if (borderValue == null) {
				borderValue = new Scalar(-1);
			}
			Imgproc.erode(src, dst, kernel, anchor, (int) iterations, borderType, borderValue);
		}

		/**
		 * Sets the values of pixels in a binary image to their distance to the
		 * nearest black pixel.
		 * 
		 * @param input
		 *            The image on which to perform the Distance Transform.
		 * @param type
		 *            The Transform.
		 * @param maskSize
		 *            the size of the mask.
		 * @param output
		 *            The image in which to store the output.
		 */
		private void findContours(Mat input, boolean externalOnly, List<MatOfPoint> contours) {
			Mat hierarchy = new Mat();
			contours.clear();
			int mode;
			if (externalOnly) {
				mode = Imgproc.RETR_EXTERNAL;
			} else {
				mode = Imgproc.RETR_LIST;
			}
			int method = Imgproc.CHAIN_APPROX_SIMPLE;
			Imgproc.findContours(input, contours, hierarchy, mode, method);
		}

		/**
		 * Filters out contours that do not meet certain criteria.
		 * 
		 * @param inputContours
		 *            is the input list of contours
		 * @param output
		 *            is the the output list of contours
		 * @param minArea
		 *            is the minimum area of a contour that will be kept
		 * @param minPerimeter
		 *            is the minimum perimeter of a contour that will be kept
		 * @param minWidth
		 *            minimum width of a contour
		 * @param maxWidth
		 *            maximum width
		 * @param minHeight
		 *            minimum height
		 * @param maxHeight
		 *            maximimum height
		 * @param Solidity
		 *            the minimum and maximum solidity of a contour
		 * @param minVertexCount
		 *            minimum vertex Count of the contours
		 * @param maxVertexCount
		 *            maximum vertex Count
		 * @param minRatio
		 *            minimum ratio of width to height
		 * @param maxRatio
		 *            maximum ratio of width to height
		 */
		private void filterContours(List<MatOfPoint> inputContours, double minArea, double minPerimeter,
				double minWidth, double maxWidth, double minHeight, double maxHeight, double[] solidity,
				double maxVertexCount, double minVertexCount, double minRatio, double maxRatio,
				List<MatOfPoint> output) {
			final MatOfInt hull = new MatOfInt();
			output.clear();
			// operation
			for (int i = 0; i < inputContours.size(); i++) {
				final MatOfPoint contour = inputContours.get(i);
				final Rect bb = Imgproc.boundingRect(contour);
				if (bb.width < minWidth || bb.width > maxWidth)
					continue;
				if (bb.height < minHeight || bb.height > maxHeight)
					continue;
				final double area = Imgproc.contourArea(contour);
				if (area < minArea)
					continue;
				if (Imgproc.arcLength(new MatOfPoint2f(contour.toArray()), true) < minPerimeter)
					continue;
				Imgproc.convexHull(contour, hull);
				MatOfPoint mopHull = new MatOfPoint();
				mopHull.create((int) hull.size().height, 1, CvType.CV_32SC2);
				for (int j = 0; j < hull.size().height; j++) {
					int index = (int) hull.get(j, 0)[0];
					double[] point = new double[] { contour.get(index, 0)[0], contour.get(index, 0)[1] };
					mopHull.put(j, 0, point);
				}
				final double solid = 100 * area / Imgproc.contourArea(mopHull);
				if (solid < solidity[0] || solid > solidity[1])
					continue;
				if (contour.rows() < minVertexCount || contour.rows() > maxVertexCount)
					continue;
				final double ratio = bb.width / (double) bb.height;
				if (ratio < minRatio || ratio > maxRatio)
					continue;
				output.add(contour);
			}
		}

	}

}
