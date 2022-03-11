package com.thegongoliers.output.motors;

public interface MotorModule {

    /**
     * Run the motor module
     * @param currentSpeed the current speed of the motor
     * @param desiredSpeed the desired speed of the motor
     * @param deltaTime the delta time since the last call in seconds
     * @return the speed the motor should set
     */
    double run(double currentSpeed, double desiredSpeed, double deltaTime);

    /**
     * Determines if the module should override the user's input
     * @return true if it overrides the user's input
     */
    default boolean overridesUser(){
        return false;
    }

}
