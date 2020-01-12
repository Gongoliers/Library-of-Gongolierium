package com.thegongoliers.input.vision;

public interface TargetingCamera {

    /**
     * Determines if the camera can see a target
     */
    public boolean hasTarget();

    /**
     * Gets the horizontal offset from the center of the camera to the target (in degrees)
     */
    public double getHorizontalOffset();

    /**
     * Gets the vertical offset from the center of the camera to the target (in degrees)
     */
    public double getVerticalOffset();

    /**
     * Gets the target area as a percent of the image (from 0 to 100)
     */
    public double getTargetArea();
}