package com.thegongoliers.math;

public class VisionMath {


    private VisionMath(){}

    /**
     * Get the distance to a target
     * @param mountAngle The vertical angle (degrees) that the camera is mounted from horizontal (positive is facing up, negative facing down)
     * @param mountHeight The height of the camera from the ground (to center of the camera)
     * @param targetAngle The vertical offset (degrees) that the target is from the center of the camera (positive is facing up, negative facing down)
     * @param targetHeight The height of the target from the ground (to center of the target)
     * @return The distance to the target in the same units as the supplied heights.
     */
    public static double getDistance(double mountAngle, double mountHeight, double targetAngle, double targetHeight){
        double goalAngle = Math.toRadians(mountAngle + targetAngle);
        return (targetHeight - mountHeight) / Math.tan(goalAngle);
    }
    
}
