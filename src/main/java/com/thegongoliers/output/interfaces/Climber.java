package com.thegongoliers.output.interfaces;

/**
 * Represents a mechanism which can climb up or down.
 */
public interface Climber extends Stoppable {

    /**
     * Climb at a given speed.
     * @param speed A percent speed between -1 and 1, inclusive. 1 is up and -1 is down.
     */
    void climb(double speed);
}