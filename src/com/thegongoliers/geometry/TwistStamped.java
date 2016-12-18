package com.thegongoliers.geometry;

public class TwistStamped {
	public Header header;
	public Twist twist;

	public TwistStamped(Header h, Twist t) {
		header = h;
		twist = t;
	}
}
