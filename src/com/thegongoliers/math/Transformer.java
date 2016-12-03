package com.thegongoliers.math;

import java.util.HashMap;

public class Transformer {
	private HashMap<String, Pose> frames;

	public static final String ORIGIN = "origin";

	public Transformer() {
		Pose origin = new Pose(0, 0, 0);
		frames = new HashMap<>();
		frames.put(ORIGIN, origin);
	}

	public Pose lookup(String frame) {
		return frames.get(frame);
	}

	public Pose lookup(String fromFrame, String toFrame) {
		Pose f1 = lookup(fromFrame);
		Pose f2 = lookup(toFrame);
		Pose diff = new Pose(f2.getX() - f1.getX(), f2.getY() - f1.getY(), f2.getRotation() - f1.getRotation());
		return diff;
	}

	public Pose transform(Pose p, String fromFrame, String toFrame) {
		Pose trans = lookup(fromFrame, toFrame);
		return transform2d(p, trans);
	}

	public Pose transformToOrigin(Pose p, String fromFrame) {
		return transform(p, fromFrame, ORIGIN);
	}

	public void put(String frame, Pose location) {
		frames.put(frame, location);
	}

	private Pose transform2d(Pose p, Pose trans) {

		double angle = Math.toRadians(trans.getRotation());
		double x = Math.cos(angle) * p.getX() + Math.sin(angle) * p.getY() + trans.getX();
		double y = -Math.sin(angle) * p.getX() + Math.cos(angle) * p.getY() + trans.getY();
		return new Pose(x, y, angle);
	}
}
