package com.thegongoliers.output.interfaces;

public interface ISolenoid {

    /**
     * Set the solenoid.
     * @param on The value of the solenoid.
     */
    void set(boolean on);

    /**
     * Determines if the solenoid is on.
     * @return True if the solenoid is on.
     */
    boolean get();

}
