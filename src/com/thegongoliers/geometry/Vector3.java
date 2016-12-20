package com.thegongoliers.geometry;

/**
 * Represents a direction as a 3D vector
 *
 */
public class Vector3 {

	public static final Vector3 i = new Vector3(1, 0, 0);
	public static final Vector3 j = new Vector3(0, 1, 0);
	public static final Vector3 k = new Vector3(0, 0, 1);
	public static final Vector3 zero = new Vector3(0, 0, 0);

	public double x, y, z;

	public Vector3(Point p) {
		this(p.x, p.y, p.z);
	}

	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3 add(Vector3 other) {
		return new Vector3(x + other.x, y + other.y, z + other.z);
	}

	public Vector3 add(double other) {
		return new Vector3(x + other, y + other, z + other);
	}

	public Vector3 subtract(Vector3 other) {
		return new Vector3(x - other.x, y - other.y, z - other.z);
	}

	public Vector3 subtract(double other) {
		return new Vector3(x - other, y - other, z - other);
	}

	public Vector3 multiply(double other) {
		return new Vector3(x * other, y * other, z * other);
	}

	public Vector3 divide(double other) {
		return new Vector3(x / other, y / other, z / other);
	}

	public double dot(Vector3 other) {
		double sum = x * other.x + y * other.y + z * other.z;
		return sum;
	}

	public Vector3 cross(Vector3 other) {
		return new Vector3(y * other.z - y * z, x * other.z - z * other.x, x * other.y - y * other.x);
	}

	public double magnitude() {
		double squaredSum = x * x + y * y + z * z;
		return Math.sqrt(squaredSum);
	}

	public Vector3 normalize() {
		return divide(magnitude());
	}
	
	public static Vector3 fromPoints(Point from, Point to){
		return new Vector3(to.subtract(from));
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Vector3)) return false;
		Vector3 other = (Vector3) obj;
		return other.x == x && other.y == y && other.z == z;
	}
	
	@Override
	public String toString() {
		return String.format("<Vector3 %1$f %2$f %3$f>", x, y, z);
	}

}
