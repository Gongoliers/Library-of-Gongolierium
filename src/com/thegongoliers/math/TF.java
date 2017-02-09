package com.thegongoliers.math;

import java.util.HashMap;
import java.util.List;

import com.thegongoliers.geometry.Point;
import com.thegongoliers.geometry.Pose;
import com.thegongoliers.geometry.Quaternion;
import com.thegongoliers.geometry.Transform;
import com.thegongoliers.geometry.Vector3;

@Deprecated
public class TF {
	private HashMap<String, TreeNode<Transform>> frames;

	public static final String ORIGIN = "origin";

	/**
	 * Create a transformation map.
	 */
	public TF() {
		Transform origin = new Transform(Vector3.zero, Quaternion.zero);
		TreeNode<Transform> root = new TreeNode<Transform>(origin);
		frames = new HashMap<>();
		frames.put(ORIGIN, root);
	}

	/**
	 * Lookup a frame in the transformation map.
	 * 
	 * @param frame
	 *            The frame to get.
	 * @return The frame.
	 */
	public Transform lookup(String frame) {
		return frames.get(frame).getData();
	}

	/**
	 * Transform a point from one frame to another.
	 * 
	 * @param p
	 *            The point p.
	 * @param fromFrame
	 *            The frame which contains point p.
	 * @param toFrame
	 *            The destination frame to move point p to.
	 * @return A point representing point p, but in the destination frame.
	 */
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

	/**
	 * Transform a point from a frame to the origin.
	 * 
	 * @param p
	 *            The point p.
	 * @param fromFrame
	 *            The frame which contains point p.
	 * @return A point representing point p, but in the origin frame.
	 */
	public Point transformToOrigin(Point p, String fromFrame) {
		return transform(p, fromFrame, ORIGIN);
	}

	/**
	 * Put a frame in the map.
	 * 
	 * @param frame
	 *            The name of the frame to add.
	 * @param parent
	 *            The name of the parent frame.
	 * @param location
	 *            The location of the frame relative to the parent frame.
	 */
	public void put(String frame, String parent, Pose location) {
		TreeNode<Transform> p = frames.get(parent);
		Transform t = new Transform(new Vector3(location.position), location.orientation).inverse();
		TreeNode<Transform> child = new TreeNode<>(t);
		child.setParent(p);
		p.getChildren().add(child);
		frames.put(frame, child);
	}

	/**
	 * Put a frame in the map.
	 * 
	 * @param frame
	 *            The name of the frame to add.
	 * @param location
	 *            The location of the frame relative to the origin.
	 */
	public void put(String frame, Pose location) {
		put(frame, ORIGIN, location);
	}
}
