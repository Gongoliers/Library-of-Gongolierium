package com.thegongoliers.input.odometry;

import com.thegongoliers.math.SwerveKinematics;
import com.thegongoliers.output.drivetrain.swerve.SwerveModule;
import com.thegongoliers.utils.Resettable;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class SwervePositionSensor implements Resettable {

    private final SwerveModule frontLeft;
    private final SwerveModule frontRight;
    private final SwerveModule backLeft;
    private final SwerveModule backRight;
    private final Gyro gyro;
    private final SwerveDriveOdometry mOdometry;

    public SwervePositionSensor(SwerveModule frontLeft, SwerveModule frontRight, SwerveModule backLeft, SwerveModule backRight, Gyro gyro) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;
        this.gyro = gyro;
        var kinematics = new SwerveKinematics(frontLeft, frontRight, backLeft, backRight);
        mOdometry = new SwerveDriveOdometry(kinematics.getKinematics(), gyro.getRotation2d());
    }

    public void update() {
        mOdometry.update(
                gyro.getRotation2d(),
                frontLeft.getState(),
                frontRight.getState(),
                backLeft.getState(),
                backRight.getState());
    }

    @Override
    public void reset() {
        mOdometry.resetPosition(new Pose2d(0, 0, Rotation2d.fromDegrees(0)), gyro.getRotation2d());
    }

    public double getX() {
        return mOdometry.getPoseMeters().getX();
    }

    public double getY() {
        return mOdometry.getPoseMeters().getY();
    }

    public double getRotation() {
        return gyro.getAngle();
    }
}
