package com.thegongoliers.output.interfaces;

/**
 * Represents a mechanism which consists of some type of bar which pivots on a motor.
 */
public interface Arm extends Stoppable {

    /**
     * @param angle the angle of the arm in degrees.
     */
    void setAngle(double angle);


    /**
     * @return the current angle of the arm in degrees.
     */
    double getAngle();

}