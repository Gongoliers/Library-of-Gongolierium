package com.thegongoliers.output.drivetrain;

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
     * Get a double configuration value in the drive module
     * @param name the name of the value
     * @return the value's contents
     */
    double getDoubleValue(String name);

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
     * @param deltaTime the delta time since the last call in seconds
     * @return the speed the drivetrain should set
     */
    DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime);

    /**
     * Get the name of the module
     * @return the module's name
     */
    String getName();

}