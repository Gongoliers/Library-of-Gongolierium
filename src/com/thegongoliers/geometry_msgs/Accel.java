package com.thegongoliers.geometry_msgs;

public class Accel {
	public Vector3 linear, angular;

	public Accel(Vector3 linear, Vector3 angular) {
		this.linear = linear;
		this.angular = angular;
	}
}
