package com.thegongoliers.math;

import jaci.pathfinder.Waypoint;

public class PathWaypoint {

    private double x, y, heading;

    /**
     * Create a path waypoint.
     * @param x The x position relative to the robot in meters.
     * @param y The y position relative to the robot in meters.
     * @param heading The heading of the robot in degrees.
     */
    public PathWaypoint(double x, double y, double heading) {
        this.x = x;
        this.y = y;
        this.heading = heading;
    }


    /**
     * Convert the path waypoint to a waypoint for trajectory calculations.
     * @return The waypoint.
     */
    public Waypoint toWaypoint(){
        return new Waypoint(x, y, Math.toRadians(heading));
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

    /**
     * Sets the heading in degrees.
     * @return  The heading.
     */
    public double getHeading() {
        return heading;
    }

    /**
     * Sets the heading in degrees.
     * @param heading The heading..
     */
    public void setHeading(double heading) {
        this.heading = heading;
    }
}
