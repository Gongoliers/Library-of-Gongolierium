package com.thegongoliers.geometry;

public class Transform {
	public Vector3 translation;
	public Quaternion rotation;

	public Transform(Vector3 translation, Quaternion rotation) {
		this.translation = translation;
		this.rotation = rotation;
	}

	public Transform inverse() {
		return new Transform(translation.multiply(-1), rotation.inverse());
	}
}
