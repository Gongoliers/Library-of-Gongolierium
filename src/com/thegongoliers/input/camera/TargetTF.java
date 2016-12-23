package com.thegongoliers.input.camera;

import com.thegongoliers.geometry.Cylindrical;
import com.thegongoliers.geometry.Point;
import com.thegongoliers.input.camera.Camera.TargetReport;
import com.thegongoliers.math.MathExt;
import com.thegongoliers.math.TF;

public class TargetTF {
	public static TargetReport transform(TargetReport report, TF map, String from, String to) {
		Cylindrical camera = new Cylindrical(report.distance(), report.angle(), 0);
		Point cartesian = MathExt.toCartesian(camera);
		Point transformedCartesian = map.transform(cartesian, from, to);
		Cylindrical transformedFinal = MathExt.toCylindrical(transformedCartesian);
		return new TargetReport(report.confidence(), transformedFinal.theta, transformedFinal.theta,
				report.aimingCoordinates());
	}

	public static TargetReport transform(TargetReport report, TF map, String from) {
		Cylindrical camera = new Cylindrical(report.distance(), report.angle(), 0);
		Point cartesian = MathExt.toCartesian(camera);
		Point transformedCartesian = map.transformToOrigin(cartesian, from);
		Cylindrical transformedFinal = MathExt.toCylindrical(transformedCartesian);
		return new TargetReport(report.confidence(), transformedFinal.theta, transformedFinal.theta,
				report.aimingCoordinates());
	}
}
