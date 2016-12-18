package com.thegongoliers.geometry;

public class QuaternionStamped {
	public Header header;
	public Quaternion quaternion;

	public QuaternionStamped(Header header, Quaternion quaternion) {
		this.header = header;
		this.quaternion = quaternion;
	}

}
