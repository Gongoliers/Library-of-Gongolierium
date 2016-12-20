package com.thegongoliers.geometry;

/**
 * The position of a point in 3D
 *
 */
public class Point {
	public double x, y, z;

	public static final Point origin = new Point(0, 0, 0);

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

	public Point add(Vector3 other) {
		return new Point(x + other.x, y + other.y, z + other.z);
	}

	public Point subtract(Point other) {
		return new Point(x - other.x, y - other.y, z - other.z);
	}

	public Point subtract(double other) {
		return new Point(x - other, y - other, z - other);
	}

	public Point subtract(Vector3 other) {
		return new Point(x - other.x, y - other.y, z - other.z);
	}

	public Point multiply(double other) {
		return new Point(x * other, y * other, z * other);
	}

	public Point divide(double other) {
		return new Point(x / other, y / other, z / other);
	}
	
	public Vector3 toVector(){
		return new Vector3(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Point))
			return false;
		Point other = (Point) obj;
		return other.x == x && other.y == y && other.z == z;
	}

	@Override
	public String toString() {
		return "<Point " + x + ", " + y + ", " + z + ">";
	}

}
