package com.thegongoliers.output.drivetrain.swerve;

public class SwerveModuleSpeed {

    private final double speed;
    private final double angle;

    public SwerveModuleSpeed(double speed, double angle) {
        this.speed = speed;
        this.angle = angle;
    }

    /**
     * The speed in percent
     *
     * @return the speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * The angle in degrees
     *
     * @return the angle
     */
    public double getAngle() {
        return angle;
    }
}
