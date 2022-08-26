package com.thegongoliers.output.drivetrain.swerve;

import com.thegongoliers.input.odometry.EncoderSensor;
import com.thegongoliers.input.time.Clock;
import com.thegongoliers.output.control.MotionController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class SwerveModule {

    private final MotorController mDriveMotor;
    private final MotorController mTurnMotor;
    private final EncoderSensor mDriveEncoder;
    private final EncoderSensor mTurnEncoder;
    private final Translation2d mLocation;
    private final MotionController mAngleController;

    private final Clock mClock;

    private double lastTime = 0.0;

    private double mStopThreshold = 0.001;

    public SwerveModule(MotorController driveMotor,
                        MotorController turnMotor,
                        EncoderSensor driveEncoder,
                        EncoderSensor turnEncoder,
                        Translation2d location,
                        MotionController angleController,
                        Clock clock) {

        mDriveMotor = driveMotor;
        mTurnMotor = turnMotor;
        mDriveEncoder = driveEncoder;
        mTurnEncoder = turnEncoder;
        mLocation = location;
        mAngleController = angleController;
        mClock = clock;
    }

    public Translation2d getLocation() {
        return mLocation;
    }

    public double getAngle() {
        return mTurnEncoder.getDistance();
    }

    public double getAngularVelocity() {
        return mTurnEncoder.getVelocity();
    }

    public double getDistance() {
        return mDriveEncoder.getDistance();
    }

    public double getVelocity() {
        return mDriveEncoder.getVelocity();
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(getVelocity(), Rotation2d.fromDegrees(getAngle()));
    }

    public void setStopThreshold(double threshold) {
        mStopThreshold = Math.abs(threshold);
    }

    public void reset() {
        mDriveEncoder.reset();
        mTurnEncoder.reset();
        mAngleController.reset();
    }

    public void set(SwerveModuleSpeed speed){
        set(speed.getSpeed(), speed.getAngle());
    }

    public void set(double driveSpeed, double angle) {
        double dt = mClock.getTime() - lastTime;
        if (dt > 0.1) {
            dt = 0.01;
        }
        lastTime = mClock.getTime();

        var state = SwerveModuleState.optimize(
                new SwerveModuleState(driveSpeed, Rotation2d.fromDegrees(angle)),
                new Rotation2d(getAngle()));

        if (Math.abs(state.speedMetersPerSecond) < mStopThreshold) {
            stop();
            return;
        }

        mDriveMotor.set(state.speedMetersPerSecond);
        mAngleController.setSetpoint(state.angle.getDegrees());
        mTurnMotor.set(mAngleController.calculate(getAngle(), dt));
    }

    public void stop() {
        mDriveMotor.set(0);
        mTurnMotor.set(0);
    }

}
