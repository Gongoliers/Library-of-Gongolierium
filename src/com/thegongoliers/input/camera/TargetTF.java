package com.thegongoliers.input.camera;

import com.thegongoliers.geometry.Cylindrical;
import com.thegongoliers.geometry.Point;
import com.thegongoliers.input.camera.Camera.TargetReport;
import com.thegongoliers.math.MathExt;
import com.thegongoliers.math.TF;

public class TargetTF {
	public static TargetReport transform(TargetReport report, TF map, String from, String to) {
		Cylindrical camera = new Cylindrical(report.distance(), Math.toRadians(report.angle()), 0);
		Point cartesian = MathExt.toCartesian(camera);
		Point transformedCartesian = map.transform(cartesian, from, to);
		Cylindrical transformedFinal = MathExt.toCylindrical(transformedCartesian);
		return new TargetReport(report.confidence(), Math.toDegrees(transformedFinal.theta), transformedFinal.r,
				report.aimingCoordinates());
	}

	public static TargetReport transform(TargetReport report, TF map, String from) {
		return transform(report, map, from, TF.ORIGIN);
	}
}
