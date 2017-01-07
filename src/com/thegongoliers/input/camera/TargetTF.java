package com.thegongoliers.input.camera;

import com.thegongoliers.geometry.Cylindrical;
import com.thegongoliers.geometry.Point;
import com.thegongoliers.input.camera.Camera.TargetReport;
import com.thegongoliers.math.MathExt;
import com.thegongoliers.math.TF;

/**
 * Used to transform TargetReports.
 *
 */
public class TargetTF {

	/**
	 * Transform a TargetReport from one location to another.
	 * 
	 * @param report
	 *            The TargetReport at it's original location.
	 * @param map
	 *            The TF which maps all of the to and from locations to each
	 *            other through transformations.
	 * @param from
	 *            The source of the TargetReport.
	 * @param to
	 *            The destination of the TargetReport.
	 * @return A TargetReport from the perspective of the to location.
	 */
	public static TargetReport transform(TargetReport report, TF map, String from, String to) {
		Cylindrical camera = new Cylindrical(report.distance(), Math.toRadians(report.angle()), 0);
		Point cartesian = MathExt.toCartesian(camera);
		Point transformedCartesian = map.transform(cartesian, from, to);
		Cylindrical transformedFinal = MathExt.toCylindrical(transformedCartesian);
		return new TargetReport(report.confidence(), Math.toDegrees(transformedFinal.theta), transformedFinal.r,
				report.aimingCoordinates());
	}

	/**
	 * Transform a TargetReport from a location to the origin.
	 * 
	 * @param report
	 *            The TargetReport at it's original location.
	 * @param map
	 *            The TF which maps all of the to and from locations to each
	 *            other through transformations.
	 * @param from
	 *            The source of the TargetReport.
	 * 
	 * @return A TargetReport from the perspective of the origin.
	 */
	public static TargetReport transform(TargetReport report, TF map, String from) {
		return transform(report, map, from, TF.ORIGIN);
	}
}
