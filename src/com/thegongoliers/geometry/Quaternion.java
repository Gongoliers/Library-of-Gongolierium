package com.thegongoliers.geometry;

@Deprecated
public class Quaternion {
	public double x, y, z, w;
	
	public static final Quaternion zero = new Quaternion(1, 0, 0, 0);

	public Quaternion(double w, double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Quaternion(double theta, Vector3 axis) {
		Vector3 qAxis = axis.normalize().multiply(Math.sin(theta / 2.0));
		w = Math.cos(theta / 2.0);
		x = qAxis.x;
		y = qAxis.y;
		z = qAxis.z;
	}

	public Quaternion add(Quaternion other) {
		return new Quaternion(w + other.w, x + other.x, y + other.y, z + other.z);
	}

	public Quaternion subtract(Quaternion other) {
		return new Quaternion(w - other.w, x - other.x, y - other.y, z - other.z);
	}

	public Quaternion inverse() {
		return new Quaternion(w, -x, -y, -z);
	}

	public Quaternion multiply(Quaternion other) {
		double nW = w * other.w - x * other.x - y * other.y - z * other.z;
		double nX = w * other.x + x * other.w + y * other.z - z * other.y;
		double nY = w * other.y - x * other.z + y * other.w + z * other.x;
		double nZ = w * other.z + x * other.y - y * other.x + z * other.w;
		return new Quaternion(nW, nX, nY, nZ);
	}

	public Quaternion multiply(Point point) {
		return multiply(new Quaternion(0, point.x, point.y, point.z));
	}

	public Quaternion multiply(Vector3 axis) {
		return multiply(new Quaternion(0, axis.x, axis.y, axis.z));
	}

	public Point rotate(Point p) {
		Quaternion out = multiply(p).multiply(inverse());
		return new Point(out.x, out.y, out.z);
	}
}
