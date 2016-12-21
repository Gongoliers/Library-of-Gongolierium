package com.thegongoliers.geometry;

public class Header {
	public static final String NO_FRAME = "0", GLOBAL_FRAME = "1";

	public int seq;
	public long timestamp;
	public String frame;

	public Header(int seq, long timestamp, String frame) {
		this.seq = seq;
		this.timestamp = timestamp;
		this.frame = frame;
	}

	public Header(int seq, long timestamp) {
		this(seq, timestamp, NO_FRAME);
	}

	public Header(int seq) {
		this(seq, System.currentTimeMillis());
	}

	public Header(long timestamp) {
		this(0, timestamp);
	}

	public Header(String frame) {
		this(0, frame);
	}

	public Header(int seq, String frame) {
		this(seq, System.currentTimeMillis(), frame);
	}

	public Header(long timestamp, String frame) {
		this(0, timestamp, frame);
	}

	public Header() {
		this(0);
	}
}
