package com.thegongoliers.output.drivetrain;

public class InvertModule implements DriveModule {

    private boolean mIsEnabled;

    public InvertModule() {
        this(false);
    }

    public InvertModule(boolean enabled) {
        mIsEnabled = enabled;
    }

    public void setEnabled(boolean enabled) {
        mIsEnabled = enabled;
    }

    public boolean isEnabled() {
        return mIsEnabled;
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
        if (!mIsEnabled) return desiredSpeed;

        // Right and left swaps
        return new DriveSpeed(-desiredSpeed.getRightSpeed(), -desiredSpeed.getLeftSpeed());
    }
}
