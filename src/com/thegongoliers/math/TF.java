package com.thegongoliers.math;

import java.util.HashMap;

import com.thegongoliers.geometry_msgs.Point;
import com.thegongoliers.geometry_msgs.Pose;
import com.thegongoliers.geometry_msgs.Quaternion;
import com.thegongoliers.geometry_msgs.Transform;
import com.thegongoliers.geometry_msgs.Vector3;

public class TF {
	private HashMap<String, Pose> frames;

	public static final String ORIGIN = "origin";

	public TF() {
		Pose origin = new Pose(Point.origin, Quaternion.zero);
		frames = new HashMap<>();
		frames.put(ORIGIN, origin);
	}

	public Pose lookup(String frame) {
		return frames.get(frame);
	}

	public Transform lookup(String fromFrame, String toFrame) {
		Pose f1 = lookup(fromFrame);
		Pose f2 = lookup(toFrame);
		Transform f1Trans = new Transform(new Vector3(f1.position), f1.orientation).inverse();
		Transform f2Trans = new Transform(new Vector3(f2.position), f2.orientation);
		Vector3 newTranslation = f1Trans.translation.add(f2Trans.translation);
		Quaternion newRotation = f2.orientation.multiply(f1.orientation);
		return new Transform(newTranslation, newRotation);
	}

	public Pose transform(Point p, String fromFrame, String toFrame) {
		Transform trans = lookup(fromFrame, toFrame);
		Point rotation = trans.rotation.rotate(p);
		Point location = rotation.subtract(trans.translation);
		return new Pose(location, Quaternion.zero);
	}

	public Pose transformToOrigin(Point p, String fromFrame) {
		return transform(p, fromFrame, ORIGIN);
	}

	public void put(String frame, Pose location) {
		frames.put(frame, location);
	}

}
