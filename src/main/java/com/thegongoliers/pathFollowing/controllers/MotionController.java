package com.thegongoliers.pathFollowing.controllers;

public interface MotionController {

    /**
     * Calculate the power to move to the target position
     * @param current the current position
     * @param target the target position
     * @return the power to move to the target position
     */
    double calculate(double current, double target);

    /**
     * Determines if the mechanism is on target
     * @param current the current position
     * @param target the target position
     * @return true if the mechanism is on target otherwise false
     */
    boolean isOnTarget(double current, double target);

}
