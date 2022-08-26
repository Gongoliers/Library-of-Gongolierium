package com.thegongoliers.output.drivetrain.swerve;

public class SwerveModuleSpeeds {

    private final SwerveModuleSpeed frontLeft;
    private final SwerveModuleSpeed frontRight;
    private final SwerveModuleSpeed backLeft;
    private final SwerveModuleSpeed backRight;

    public SwerveModuleSpeeds(SwerveModuleSpeed frontLeft, SwerveModuleSpeed frontRight, SwerveModuleSpeed backLeft, SwerveModuleSpeed backRight) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;
    }

    public SwerveModuleSpeed getFrontLeft() {
        return frontLeft;
    }

    public SwerveModuleSpeed getFrontRight() {
        return frontRight;
    }

    public SwerveModuleSpeed getBackLeft() {
        return backLeft;
    }

    public SwerveModuleSpeed getBackRight() {
        return backRight;
    }
}
