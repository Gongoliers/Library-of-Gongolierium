package com.thegongoliers.output.drivemodules;

import java.util.Set;

/**
 * A drive module which can be added to a ModularDrivetrain to enhance its functionality
 */
public interface DriveModule {

    /**
     * Get a configurable value in the drive module
     * @param name the name of the value
     * @return the value's contents as an Object
     */
    Object getValue(String name);

    /**
     * Set a configurable value in the drive module
     * @param name the name of the value
     * @param value the value's contents
     */
    void setValue(String name, Object value);

    /**
     * @return the set of supported configurable value names
     */
    Set<String> getValueNames();

    /**
     * Run the drive module
     * @param currentSpeed the current speed of the drivetrain
     * @param desiredSpeed the desired speed of the drivetrain
     * @return the speed the drivetrain should set
     */
    DriveValue run(DriveValue currentSpeed, DriveValue desiredSpeed);

    /**
     * Get the order in which this module should be run. Modules with lower orders will be run before those of higher orders.
     * @return the module order
     */
    int getOrder();

}