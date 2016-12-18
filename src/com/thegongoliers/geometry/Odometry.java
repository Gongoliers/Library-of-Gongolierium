package com.thegongoliers.geometry;

public class Odometry {
	public PoseStamped pose;
	public TwistStamped twist;

	public Odometry(PoseStamped p, TwistStamped t) {
		pose = p;
		twist = t;
	}
}
