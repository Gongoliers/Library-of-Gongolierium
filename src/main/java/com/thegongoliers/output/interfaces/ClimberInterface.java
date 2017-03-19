package com.thegongoliers.output.interfaces;

/**
 * Represents a climber to be used by a robot to climb up on obstacle.
 */
public interface ClimberInterface extends Stoppable {

    /**
     * Climb up at the given speed
     *
     * @param speed The speed of the lifting mechanism from 0 to 1 inclusive
     */
    void up(double speed);

    /**
     * Determine if the climbing mechanism is at the bottom
     *
     * @return True if the climbing mechanism is at the absolute bottom, false
     * otherwise
     */
    boolean isAtBottom();

    /**
     * Determine if the climbing mechanism is at the top
     *
     * @return True if the climbing mechanism is at the absolute top, false
     * otherwise
     */
    boolean isAtTop();

    /**
     * Get the position of the climbing mechanism from 0 to 1 inclusive along its
     * track where 0 is the bottom, and 1 is the top
     *
     * @return The position of the climbing mechanism
     */
    double getPosition();

}
