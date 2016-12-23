package com.thegongoliers.input.camera;

import com.thegongoliers.geometry.Point;

interface TargetReportInterface {

	int confidence();

	double angle();

	double distance();

	Point aimingCoordinates();

}
