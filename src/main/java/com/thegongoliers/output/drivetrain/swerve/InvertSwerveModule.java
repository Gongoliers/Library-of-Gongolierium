package com.thegongoliers.output.drivetrain.swerve;

import com.thegongoliers.utils.IModule;

public class InvertSwerveModule implements IModule<SwerveSpeed> {

    private boolean mIsEnabled;

    public InvertSwerveModule() {
        this(false);
    }

    public InvertSwerveModule(boolean enabled) {
        mIsEnabled = enabled;
    }

    public void setEnabled(boolean enabled) {
        mIsEnabled = enabled;
    }

    public boolean isEnabled() {
        return mIsEnabled;
    }

    @Override
    public SwerveSpeed run(SwerveSpeed currentSpeed, SwerveSpeed desiredSpeed, double deltaTime) {
        if (!mIsEnabled) return desiredSpeed;

        return new SwerveSpeed(-desiredSpeed.getX(), -desiredSpeed.getY(), -desiredSpeed.getRotation());
    }
}
