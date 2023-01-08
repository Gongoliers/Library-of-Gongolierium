package com.thegongoliers.input.odometry;

import com.thegongoliers.math.SwerveKinematics;
import com.thegongoliers.output.drivetrain.swerve.SwerveModule;
import com.thegongoliers.utils.Resettable;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class SwervePositionSensor implements Resettable {

    private final SwerveModule frontLeft;
    private final SwerveModule frontRight;
    private final SwerveModule backLeft;
    private final SwerveModule backRight;
    private final Gyro gyro;
    private final SwerveDriveOdometry mOdometry;
    private final SwerveModulePosition[] mPositions;

    public SwervePositionSensor(SwerveModule frontLeft, SwerveModule frontRight, SwerveModule backLeft, SwerveModule backRight, Gyro gyro) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;
        this.gyro = gyro;
        mPositions = new SwerveModulePosition[4];
        mPositions[0] = new SwerveModulePosition(frontLeft.getDistance(), Rotation2d.fromDegrees(frontLeft.getAngle()));
        mPositions[1] = new SwerveModulePosition(frontRight.getDistance(), Rotation2d.fromDegrees(frontRight.getAngle()));
        mPositions[2] = new SwerveModulePosition(backLeft.getDistance(), Rotation2d.fromDegrees(backLeft.getAngle()));
        mPositions[3] = new SwerveModulePosition(backRight.getDistance(), Rotation2d.fromDegrees(backRight.getAngle()));
        var kinematics = new SwerveKinematics(frontLeft, frontRight, backLeft, backRight);
        mOdometry = new SwerveDriveOdometry(kinematics.getKinematics(), gyro.getRotation2d(), mPositions, new Pose2d());
    }

    public void update() {
        updatePositions();
        mOdometry.update(gyro.getRotation2d(), mPositions);
    }

    @Override
    public void reset() {
        updatePositions();
        mOdometry.resetPosition(gyro.getRotation2d(), mPositions, new Pose2d());
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

    private void updatePositions(){
        mPositions[0].distanceMeters = frontLeft.getDistance();
        mPositions[1].distanceMeters = frontRight.getDistance();
        mPositions[2].distanceMeters = backLeft.getDistance();
        mPositions[3].distanceMeters = backRight.getDistance();

        mPositions[0].angle = Rotation2d.fromDegrees(frontLeft.getAngle());
        mPositions[1].angle = Rotation2d.fromDegrees(frontRight.getAngle());
        mPositions[2].angle = Rotation2d.fromDegrees(backLeft.getAngle());
        mPositions[3].angle = Rotation2d.fromDegrees(backRight.getAngle());
    }
}
