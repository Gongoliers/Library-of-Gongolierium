package com.thegongoliers.input.odometry;

/**
 * A sensor which detects velocity
 */
@FunctionalInterface
public interface VelocitySensor {

    /**
     * @return the velocity
     */
    double getVelocity();

}
