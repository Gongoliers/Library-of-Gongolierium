package com.thegongoliers.output.interfaces;

/**
 * Represents a shooter mechanism.
 */
public interface Shooter extends Stoppable {

    /**
     * Cause the mechanism to shoot.
     */
    void shoot();

}