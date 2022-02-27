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

    public default DistanceSensor inverted(){
        return () -> -getDistance();
    }

    public default DistanceSensor scaledBy(double scale){
        return () -> scale * getDistance();
    }

}
