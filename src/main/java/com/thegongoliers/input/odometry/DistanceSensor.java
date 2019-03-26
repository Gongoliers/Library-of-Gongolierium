package com.thegongoliers.input.odometry;

/**
 * A sensor which detects the distance travelled
 */
@FunctionalInterface
public interface DistanceSensor {

    /**
     * @return the distance travelled
     */
    double getDistance();

}
