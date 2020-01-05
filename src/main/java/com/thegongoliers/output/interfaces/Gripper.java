package com.thegongoliers.output.interfaces;

/**
 * Represents a gripper mechanism.
 */
public interface Gripper {

    /**
     * Open the gripper
     */
    void open();

    /**
     * Close the gripper
     */
    void close();

    /**
     * Determine if the gripper is open
     * @return true if the gripper is open otherwise false
     */
    boolean isOpen();

}