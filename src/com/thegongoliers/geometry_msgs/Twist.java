package com.thegongoliers.geometry_msgs;

public class Twist {
	public Vector3 linear, angular;

	public Twist(Vector3 linear, Vector3 angular) {
		this.linear = linear;
		this.angular = angular;
	}
}
