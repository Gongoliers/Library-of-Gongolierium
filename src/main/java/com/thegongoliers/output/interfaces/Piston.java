package com.thegongoliers.output.interfaces;

public interface Piston {

    /**
     * Extend the piston.
     */
    void extend();

    /**
     * Retract the piston.
     */
    void retract();

    /**
     * Determines if the piston is extended.
     * @return True if the piston is extended.
     */
    boolean isExtended();

    /**
     * Determines if the piston is retracted.
     * @return True if the piston is retracted.
     */
    boolean isRetracted();

    /**
     * Invert the piston.
     * @param inverted True if the piston is inverted.
     */
    void setInverted(boolean inverted);

    /**
     * Determines if the piston is inverted.
     * @return True if the piston is inverted.
     */
    boolean isInverted();

}
