package com.thegongoliers.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class RobotSubsystem extends Subsystem {

    private boolean hasErrors = false;
    private boolean wasInitialized = false;

    /**
     * Publish data to SmartDashboard
     */
    public abstract void publish();

    /**
     * Initialize all hardware related to this subsystem.
     */
    public abstract void initialize();

    public boolean hasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public boolean wasInitialized() {
        return wasInitialized;
    }

    public void setWasInitialized(boolean wasInitialized) {
        this.wasInitialized = wasInitialized;
    }
}
