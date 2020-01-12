package com.thegongoliers.output.drivetrain;

/**
 * A drive module which can be added to a ModularDrivetrain to enhance its functionality
 */
public interface DriveModule {

    /**
     * Run the drive module
     * @param currentSpeed the current speed of the drivetrain
     * @param desiredSpeed the desired speed of the drivetrain
     * @param deltaTime the delta time since the last call in seconds
     * @return the speed the drivetrain should set
     */
    DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime);

    /**
     * Determines if the module should override the user's input
     * @return true if it overrides the user's input
     */
    default boolean overridesUser(){
        return false;
    }

}