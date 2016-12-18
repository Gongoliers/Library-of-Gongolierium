package com.thegongoliers.geometry_msgs;

public class Transform {
	public Vector3 translation;
	public Quaternion rotation;

	public Transform(Vector3 translation, Quaternion rotation) {
		this.translation = translation;
		this.rotation = rotation;
	}
}
