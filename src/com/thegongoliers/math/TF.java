package com.thegongoliers.math;

import java.util.HashMap;
import java.util.List;

import com.thegongoliers.geometry.Point;
import com.thegongoliers.geometry.Pose;
import com.thegongoliers.geometry.Quaternion;
import com.thegongoliers.geometry.Transform;
import com.thegongoliers.geometry.Vector3;

public class TF {
	private HashMap<String, TreeNode<Transform>> frames;

	public static final String ORIGIN = "origin";

	public TF() {
		Transform origin = new Transform(Vector3.zero, Quaternion.zero);
		TreeNode<Transform> root = new TreeNode<Transform>(origin);
		frames = new HashMap<>();
		frames.put(ORIGIN, root);
	}

	public Transform lookup(String frame) {
		return frames.get(frame).getData();
	}

	public Point transform(Point p, String fromFrame, String toFrame) {
		TreeSearchEngine search = new TreeSearchEngine();
		TreeNode<Transform> ancestor = search.findLowestCommonAncestor(frames.get(fromFrame), frames.get(toFrame));
		TreeNode<Transform> current = frames.get(fromFrame);
		Point currentPt = p;
		while (current != ancestor) {
			Transform c = current.getData();
			currentPt = applyTransformationTo(currentPt, c);
			current = current.getParent();

		}
		TreeNode<Transform> destination = frames.get(toFrame);
		List<TreeNode<Transform>> ancestors = search.getAllAncestors(destination);
		int stopIndex = ancestors.indexOf(ancestor);
		for (int i = stopIndex - 1; i >= 0; i--) {
			Transform c = ancestors.get(i).getData();
			currentPt = applyTransformationBack(currentPt, c);
		}
		return currentPt;
	}

	private Point applyTransformationTo(Point p, Transform t) {
		Point rotation = t.rotation.inverse().rotate(p);
		return rotation.subtract(t.translation);
	}

	private Point applyTransformationBack(Point p, Transform t) {
		return t.rotation.rotate(p.subtract(t.translation.multiply(-1)));
	}

	public Point transformToOrigin(Point p, String fromFrame) {
		return transform(p, fromFrame, ORIGIN);
	}

	public void put(String frame, String parent, Pose location) {
		TreeNode<Transform> p = frames.get(parent);
		Transform t = new Transform(new Vector3(location.position), location.orientation).inverse();
		TreeNode<Transform> child = new TreeNode<>(t);
		child.setParent(p);
		p.getChildren().add(child);
		frames.put(frame, child);
	}
	
	public void put(String frame, Pose location){
		put(frame, ORIGIN, location);
	}
}
