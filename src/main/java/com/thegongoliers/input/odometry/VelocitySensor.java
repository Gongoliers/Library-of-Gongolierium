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

    public default VelocitySensor inverted(){
        return () -> -getVelocity();
    }

    public default  VelocitySensor scaledBy(double scale){
        return () -> scale * getVelocity();
    }

}
