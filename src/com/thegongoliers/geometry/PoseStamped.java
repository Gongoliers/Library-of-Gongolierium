package com.thegongoliers.geometry;

public class PoseStamped {
	public Header header;
	public Pose pose;

	public PoseStamped(Header h, Pose p) {
		header = h;
		pose = p;
	}
}
