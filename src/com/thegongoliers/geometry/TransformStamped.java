package com.thegongoliers.geometry;

public class TransformStamped {
	public Header header;
	public String childFrame;
	public Transform transform;

	public TransformStamped(Header header, String childFrame, Transform transform) {
		this.header = header;
		this.childFrame = childFrame;
		this.transform = transform;
	}

}
