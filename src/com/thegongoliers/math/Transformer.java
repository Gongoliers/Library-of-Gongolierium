package com.thegongoliers.math;

import java.util.HashMap;

import com.thegongoliers.geometry_msgs.Pose2D;

public class Transformer {
	private HashMap<String, Pose2D> frames;

	public static final String ORIGIN = "origin";

	public Transformer() {
		Pose2D origin = new Pose2D(0, 0, 0);
		frames = new HashMap<>();
		frames.put(ORIGIN, origin);
	}

	public Pose2D lookup(String frame) {
		return frames.get(frame);
	}

	public Pose2D lookup(String fromFrame, String toFrame) {
		Pose2D f1 = lookup(fromFrame);
		Pose2D f2 = lookup(toFrame);
		Pose2D diff = new Pose2D(f1.x - f2.x, f1.y - f2.y, f2.theta - f1.theta);
		return diff;
	}

	public Pose2D transform(Pose2D p, String fromFrame, String toFrame) {
		Pose2D trans = lookup(fromFrame, toFrame);
		return transform2d(p, trans);
	}

	public Pose2D transformToOrigin(Pose2D p, String fromFrame) {
		return transform(p, fromFrame, ORIGIN);
	}

	public void put(String frame, Pose2D location) {
		frames.put(frame, location);
	}

	private Pose2D transform2d(Pose2D p, Pose2D trans) {
		double x = Math.cos(trans.theta) * p.x + Math.sin(trans.theta) * p.y + trans.x;
		double y = -Math.sin(trans.theta) * p.x + Math.cos(trans.theta) * p.y + trans.y;
		return new Pose2D(x, y, trans.theta);
	}
}
