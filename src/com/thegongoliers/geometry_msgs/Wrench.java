package com.thegongoliers.geometry_msgs;

public class Wrench {
	public Vector3 force, torque;

	public Wrench(Vector3 force, Vector3 torque) {
		this.force = force;
		this.torque = torque;
	}
}
