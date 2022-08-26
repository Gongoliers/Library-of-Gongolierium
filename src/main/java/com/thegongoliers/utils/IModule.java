package com.thegongoliers.utils;

public interface IModule<T> {
    /**
     * Run the module
     *
     * @param current   the current state
     * @param desired   the desired state
     * @param deltaTime the delta time since the last call in seconds
     * @return the new desired state
     */
    T run(T current, T desired, double deltaTime);

    /**
     * Determines if the module should override the user's input
     *
     * @return true if it overrides the user's input
     */
    default boolean overridesUser() {
        return false;
    }
}
