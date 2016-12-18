package com.thegongoliers.geometry_msgs;

/**
 * The position of a point in 3D
 *
 */
public class Point {
	public double x, y, z;

	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Point add(Point other) {
		return new Point(x + other.x, y + other.y, z + other.z);
	}

	public Point add(double other) {
		return new Point(x + other, y + other, z + other);
	}

	public Point subtract(Point other) {
		return new Point(x - other.x, y - other.y, z - other.z);
	}

	public Point subtract(double other) {
		return new Point(x - other, y - other, z - other);
	}

	public Point multiply(double other) {
		return new Point(x * other, y * other, z * other);
	}

	public Point divide(double other) {
		return new Point(x / other, y / other, z / other);
	}
	
}
