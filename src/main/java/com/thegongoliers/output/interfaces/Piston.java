package com.thegongoliers.output.interfaces;

public interface Piston extends Extendable, Retractable {
    /**
     * Invert the piston.
     *
     * @param inverted True if the piston is inverted.
     */
    void setInverted(boolean inverted);

    /**
     * Determines if the piston is inverted.
     *
     * @return True if the piston is inverted.
     */
    boolean isInverted();

}
