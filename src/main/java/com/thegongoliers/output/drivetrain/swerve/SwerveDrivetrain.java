package com.thegongoliers.output.drivetrain.swerve;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class SwerveDrivetrain {

    private final SwerveWheel mFrontLeft;
    private final SwerveWheel mFrontRight;
    private final SwerveWheel mBackLeft;
    private final SwerveWheel mBackRight;
    private final Gyro mGyro;

    private double maxDegreesPerSecond = 180;
    private double maxWheelSpeed = 1;

    private final SwerveDriveKinematics mKinematics;

    private final SwerveDriveOdometry mOdometry;

    public SwerveDrivetrain(SwerveWheel frontLeft, SwerveWheel frontRight, SwerveWheel backLeft, SwerveWheel backRight, Gyro gyro) {
        mFrontLeft = frontLeft;
        mFrontRight = frontRight;
        mBackLeft = backLeft;
        mBackRight = backRight;
        mGyro = gyro;
        mKinematics = new SwerveDriveKinematics(frontLeft.getLocation(), frontRight.getLocation(), backLeft.getLocation(), backRight.getLocation());
        mOdometry = new SwerveDriveOdometry(mKinematics, mGyro.getRotation2d());
    }

    public void setMaxDegreesPerSecond(double maxDegreesPerSecond) {
        this.maxDegreesPerSecond = maxDegreesPerSecond;
    }

    public void setMaxWheelSpeed(double maxWheelSpeed) {
        this.maxWheelSpeed = maxWheelSpeed;
    }

    /**
     * Drive with given speeds (percentages of max speed)
     *
     * @param x        The x speed [-1, 1]
     * @param y        The y speed [-1, 1]
     * @param rotation The rotation speed [-1, 1]
     */
    public void drive(double x, double y, double rotation) {
        // TODO: Add modules which take in and output x, y, rotation percentages (field relative, velocity control, voltage control, ramp, path follower, target alignment)
        var speed = new ChassisSpeeds(x * maxWheelSpeed, y * maxWheelSpeed, Math.toRadians(rotation * maxDegreesPerSecond));
        var states = mKinematics.toSwerveModuleStates(speed);
        SwerveDriveKinematics.desaturateWheelSpeeds(states, maxWheelSpeed);
        mFrontLeft.set(states[0].speedMetersPerSecond / maxWheelSpeed, states[0].angle.getDegrees());
        mFrontRight.set(states[1].speedMetersPerSecond / maxWheelSpeed, states[1].angle.getDegrees());
        mBackLeft.set(states[2].speedMetersPerSecond / maxWheelSpeed, states[2].angle.getDegrees());
        mBackRight.set(states[3].speedMetersPerSecond / maxWheelSpeed, states[3].angle.getDegrees());
    }

    /**
     * Drive with given speeds (percentages of max speed)
     *
     * @param magnitude The speed [-1, 1]
     * @param direction The direction to drive in degrees
     * @param rotation  The rotation speed [-1, 1]
     */
    public void drivePolar(double magnitude, double direction, double rotation) {
        drive(magnitude * Math.cos(Math.toRadians(direction)), magnitude * Math.sin(Math.toRadians(direction)), rotation);
    }

    public double getX() {
        return mOdometry.getPoseMeters().getX();
    }

    public double getY() {
        return mOdometry.getPoseMeters().getY();
    }

    public double getRotation() {
        return mGyro.getAngle();
    }

    public void resetPosition() {
        mOdometry.resetPosition(new Pose2d(0, 0, Rotation2d.fromDegrees(0)), mGyro.getRotation2d());
    }

    public void resetWheels() {
        mFrontLeft.reset();
        mFrontRight.reset();
        mBackLeft.reset();
        mBackRight.reset();
    }

    public void updatePosition() {
        mOdometry.update(
                mGyro.getRotation2d(),
                mFrontLeft.getState(),
                mFrontRight.getState(),
                mBackLeft.getState(),
                mBackRight.getState());
    }

}
