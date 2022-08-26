package com.thegongoliers.math;

import com.thegongoliers.output.drivetrain.swerve.SwerveModule;
import com.thegongoliers.output.drivetrain.swerve.SwerveModuleSpeed;
import com.thegongoliers.output.drivetrain.swerve.SwerveModuleSpeeds;
import com.thegongoliers.output.drivetrain.swerve.SwerveSpeed;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;

public class SwerveKinematics {

    private final double maxSpeed;
    private final double maxRotationSpeedDegrees;
    private final SwerveDriveKinematics mKinematics;

    public SwerveKinematics(SwerveModule frontLeft, SwerveModule frontRight, SwerveModule backLeft, SwerveModule backRight, double maxSpeed, double maxRotationSpeedDegrees) {
        this.maxSpeed = maxSpeed;
        this.maxRotationSpeedDegrees = maxRotationSpeedDegrees;
        mKinematics = new SwerveDriveKinematics(frontLeft.getLocation(), frontRight.getLocation(), backLeft.getLocation(), backRight.getLocation());
    }

    public SwerveKinematics(SwerveModule frontLeft, SwerveModule frontRight, SwerveModule backLeft, SwerveModule backRight) {
        this(frontLeft, frontRight, backLeft, backRight, 1, 180);
    }

    public SwerveDriveKinematics getKinematics() {
        return mKinematics;
    }

    public SwerveModuleSpeeds calculate(SwerveSpeed speed) {
        var s = new ChassisSpeeds(speed.getX() * maxSpeed, speed.getY() * maxSpeed, Math.toRadians(speed.getRotation() * maxRotationSpeedDegrees));
        var states = mKinematics.toSwerveModuleStates(s);
        SwerveDriveKinematics.desaturateWheelSpeeds(states, maxSpeed);
        return new SwerveModuleSpeeds(
                new SwerveModuleSpeed(states[0].speedMetersPerSecond / maxSpeed, states[0].angle.getDegrees()),
                new SwerveModuleSpeed(states[1].speedMetersPerSecond / maxSpeed, states[1].angle.getDegrees()),
                new SwerveModuleSpeed(states[2].speedMetersPerSecond / maxSpeed, states[2].angle.getDegrees()),
                new SwerveModuleSpeed(states[3].speedMetersPerSecond / maxSpeed, states[3].angle.getDegrees())
        );
    }

}
