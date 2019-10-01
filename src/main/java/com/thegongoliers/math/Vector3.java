package com.thegongoliers.math;

public class Vector3 {

    public static final Vector3 zero = new Vector3(0, 0, 0);
    public static final Vector3 one = new Vector3(1, 1, 1);
    public static final Vector3 back = new Vector3(0, 0, -1);
    public static final Vector3 down = new Vector3(0, -1, 0);
    public static final Vector3 forward = new Vector3(0, 0, 1);
    public static final Vector3 left = new Vector3(-1, 0, 0);
    public static final Vector3 negativeInfinity = new Vector3(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
    public static final Vector3 positiveInfinity = new Vector3(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    public static final Vector3 right = new Vector3(1, 0, 0);
    public static final Vector3 up = new Vector3(0, 1, 0);

    public final double x, y, z;

    public Vector3(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(double x, double y){
        this(x, y, 0);
    }

    public double magnitude(){
        double sum = sqrMagnitude();
        return Math.sqrt(sum);
    }

    public double sqrMagnitude(){
        return x * x + y * y + z * z;
    }

    public Vector3 normalized(){
        double magnitude = magnitude();
        return new Vector3(x / magnitude, y / magnitude, z / magnitude);
    }

    public Vector3 subtract(double other){
        return new Vector3(x - other, y - other, z - other);
    }

    public Vector3 subtractFrom(double other){
        return new Vector3(other - x, other - y, other - z);
    }

    public Vector3 subtract(Vector3 other){
        return new Vector3(x - other.x, y - other.y, z - other.z);
    }

    public Vector3 add(double other){
        return new Vector3(x + other, y + other, z + other);
    }

    public Vector3 add(Vector3 other){
        return new Vector3(x + other.x, y + other.y, z + other.z);
    }

    public Vector3 multiply(double other){
        return new Vector3(x * other, y * other, z * other);
    }

    public Vector3 divide(double other){
        return new Vector3(x / other, y / other, z / other);
    }

    public Vector3 divideFrom(double other){
        return new Vector3(other / x, other / y, other / z);
    }

    public Vector3 negate(){
        return new Vector3(-x, -y, -z);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector3)) return false;
        Vector3 other = (Vector3) obj;
        return GMath.approximately(other.x, x) && GMath.approximately(other.y, y) && GMath.approximately(other.z, z);
    }

    // Static methods

    public static double angle(Vector3 v1, Vector3 v2){
        double dot = Vector3.dot(v1.normalized(), v2.normalized());
        return Math.toDegrees(GMath.clamp(dot, -1, 1));
    }

    public static Vector3 clampMagnitude(Vector3 v1, double maxLength){
        if (v1.sqrMagnitude() > maxLength * maxLength){
            return v1.normalized().multiply(maxLength);
        }
        return v1;
    }

    public static Vector3 cross(Vector3 v1, Vector3 v2){
        double x = v1.y * v2.z - v1.z * v2.y;
        double y = v1.z * v2.x - v1.x * v2.z;
        double z = v1.x * v2.y - v1.y * v2.x;
        return new Vector3(x, y, z);
    }

    public static double distance(Vector3 v1, Vector3 v2){
        return v1.subtract(v2).magnitude();
    }

    public static double dot(Vector3 v1, Vector3 v2){
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }

    public static Vector3 lerp(Vector3 v1, Vector3 v2, double t){
        double x = GMath.lerp(v1.x, v2.x, t);
        double y = GMath.lerp(v1.y, v2.y, t);
        double z = GMath.lerp(v1.z, v2.z, t);
        return new Vector3(x, y, z);
    }

    public static Vector3 lerpUnclamped(Vector3 v1, Vector3 v2, double t){
        double x = GMath.lerpUnclamped(v1.x, v2.x, t);
        double y = GMath.lerpUnclamped(v1.y, v2.y, t);
        double z = GMath.lerpUnclamped(v1.z, v2.z, t);
        return new Vector3(x, y, z);
    }

    public static Vector3 max(Vector3 v1, Vector3 v2){
        return new Vector3(Math.max(v1.x, v2.x), Math.max(v1.y, v2.y), Math.max(v1.z, v2.z));
    }

    public static Vector3 min(Vector3 v1, Vector3 v2){
        return new Vector3(Math.min(v1.x, v2.x), Math.min(v1.y, v2.y), Math.min(v1.z, v2.z));
    }

    public static Vector3 moveTowards(Vector3 current, Vector3 target, double maxDistanceDelta){
        Vector3 diff = target.subtract(current);
        double mag = diff.magnitude();
        if (mag <= maxDistanceDelta || GMath.approximately(mag, 0)){
            return target;
        }
        return current.add(diff.divide(mag).multiply(maxDistanceDelta));
    }

    public static Vector3 project(Vector3 vector, Vector3 normal){
        double dot = Vector3.dot(normal, normal);
        if (GMath.approximately(dot, 0)) return Vector3.zero;
        return normal.multiply(Vector3.dot(vector, normal)).divide(dot);
    }

    public static Vector3 projectOnPlane(Vector3 vector, Vector3 planeNormal){
        return vector.subtract(Vector3.project(vector, planeNormal));
    }

    public static Vector3 reflect(Vector3 direction, Vector3 normal){
        return normal.multiply(-2 * Vector3.dot(normal, direction)).add(direction);
    }

    public static Vector3 scale(Vector3 v1, Vector3 v2){
        return new Vector3(v1.x * v2.x, v1.y * v2.y, v1.z * v2.z);
    }
}