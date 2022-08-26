package com.thegongoliers.output.drivetrain.swerve;

import com.thegongoliers.math.GMath;

public class SwerveSpeed {

    public static final SwerveSpeed STOP = new SwerveSpeed(0, 0, 0);

    private double x, y, rotation;

    /**
     * Default constructor
     *
     * @param x        the x speed
     * @param y        the y speed
     * @param rotation the rotation speed
     */
    public SwerveSpeed(double x, double y, double rotation) {
        this.x = GMath.clamp(x, -1, 1);
        this.y = GMath.clamp(y, -1, 1);
        this.rotation = GMath.clamp(rotation, -1, 1);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRotation() {
        return rotation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SwerveSpeed that = (SwerveSpeed) o;

        if (Double.compare(that.x, x) != 0) return false;
        if (Double.compare(that.y, y) != 0) return false;
        return Double.compare(that.rotation, rotation) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(rotation);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
