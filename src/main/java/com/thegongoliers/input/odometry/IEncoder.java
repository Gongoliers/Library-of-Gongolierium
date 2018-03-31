package com.thegongoliers.input.odometry;

public interface IEncoder {

    /**
     * Get the total number of pulses since last reset.
     * @return The number of pulses.
     */
    double getPulses();

    /**
     * Get the total distance traveled by the encoder.
     * @return The total distance.
     */
    double getDistance();

    /**
     * Get the velocity of the encoder.
     * @return The velocity.
     */
    double getVelocity();

    /**
     * Sets the distance per pulse of the encoder.
     * @param distancePerPulse The distance per pulse.
     */
    void setDistancePerPulse(double distancePerPulse);

    /**
     * Reset the pulse count.
     */
    void reset();

    /**
     * Invert the encoder.
     * @param inverted True if the encoder is inverted.
     */
    void setInverted(boolean inverted);

    /**
     * Determines if the encoder is inverted.
     * @return True if the encoder is inverted.
     */
    boolean isInverted();

}
