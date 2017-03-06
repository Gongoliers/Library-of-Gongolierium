package com.thegongoliers.input;

import com.kylecorry.geometry.Pose2D;

/**
 * A class for calculating the odometry of a robot from wheel encoders.
 */
public class Odometry {

    /**
     * Get the distance traveled by the center of the robot.
     *
     * @param left  The distance traveled by the left wheels.
     * @param right The distance traveled by the right wheels.
     * @return The distance traveled by the center of the robot.
     */
    public static double getDistance(double left, double right) {
        return (left + right) / 2.0;
    }

    /**
     * Get the angle traveled by the center of the robot.
     *
     * @param left      The distance traveled by the left wheels.
     * @param right     The distance traveled by the right wheels.
     * @param wheelBase The distance between the center of the left and right wheels.
     * @return The angle traveled by the center of the robot in radians.
     */
    public static double getAngle(double left, double right, double wheelBase) {
        return (right - left) / wheelBase;
    }

    /**
     * Get the distance traveled along the x axis by the center of the robot.
     *
     * @param left  The distance traveled by the left wheels.
     * @param right The distance traveled by the right wheels.
     * @param angle The initial angle of the robot in radians.
     * @return The distance traveled in the direction of the x axis.
     */
    public static double getX(double left, double right, double angle) {
        return getDistance(left, right) * Math.cos(angle);
    }

    /**
     * Get the distance traveled along the y axis by the center of the robot.
     *
     * @param left  The distance traveled by the left wheels.
     * @param right The distance traveled by the right wheels.
     * @param angle The initial angle of the robot in radians.
     * @return The distance traveled in the direction of the y axis.
     */
    public static double getY(double left, double right, double angle) {
        return getDistance(left, right) * Math.sin(angle);
    }

    /**
     * Convert encoder ticks to a distance.
     *
     * @param ticks            The ticks of the encoder.
     * @param ticksPerRotation The ticks per complete rotation of the encoder.
     * @param radius           The radius of the wheel.
     * @return The distance traveled by the wheel.
     */
    public static double ticksToDistance(double ticks, double ticksPerRotation, double radius) {
        return 2 * Math.PI * radius * ticks / ticksPerRotation;
    }

    /**
     * Update the pose of the robot based on the new odometry.
     *
     * @param previousPose The previous pose of the robot.
     * @param left         The distance traveled by the left wheels.
     * @param right        The distance traveled by the right wheels.
     * @param wheelBase    The distance between the center of the left and right wheels.
     * @return The new pose of the robot.
     */
    public static Pose2D updatePose(Pose2D previousPose, double left, double right, double wheelBase) {
        double x = previousPose.x + getX(left, right, previousPose.theta);
        double y = previousPose.y + getY(left, right, previousPose.theta);
        double theta = previousPose.theta + getAngle(left, right, wheelBase);
        return new Pose2D(x, y, theta);
    }

}
