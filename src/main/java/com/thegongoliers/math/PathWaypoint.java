package com.thegongoliers.math;

public class PathWaypoint {

    private double x, y;

    /**
     * Create a path waypoint.
     * @param x The x position relative to the robot in meters.
     * @param y The y position relative to the robot in meters.
     */
    public PathWaypoint(double x, double y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Gets the x position in meters.
     * @return The x position.
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the x position in meters.
     * @param x The x position.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets the y position in meters.
     * @return The y position.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the y position in meters.
     * @param y The y position.
     */
    public void setY(double y) {
        this.y = y;
    }
}