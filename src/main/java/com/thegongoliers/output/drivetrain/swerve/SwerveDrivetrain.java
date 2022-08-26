package com.thegongoliers.output.drivetrain.swerve;

import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.time.RobotClock;
import com.thegongoliers.output.interfaces.Stoppable;
import com.thegongoliers.utils.IModular;
import com.thegongoliers.utils.IModule;
import com.thegongoliers.utils.ModuleRunner;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.interfaces.Gyro;

import java.util.List;

public class SwerveDrivetrain implements IModular<SwerveSpeed>, Stoppable {

    private final SwerveModule mFrontLeft;
    private final SwerveModule mFrontRight;
    private final SwerveModule mBackLeft;
    private final SwerveModule mBackRight;
    private final Gyro mGyro;

    private double maxDegreesPerSecond = 180;
    private double maxWheelSpeed = 1;

    private final SwerveDriveKinematics mKinematics;

    private final SwerveDriveOdometry mOdometry;

    private final ModuleRunner<SwerveSpeed> modules;

    private SwerveSpeed _currentSpeed = SwerveSpeed.STOP;

    public SwerveDrivetrain(SwerveModule frontLeft, SwerveModule frontRight, SwerveModule backLeft, SwerveModule backRight, Gyro gyro) {
        this(frontLeft, frontRight, backLeft, backRight, gyro, new RobotClock());
    }

    public SwerveDrivetrain(SwerveModule frontLeft, SwerveModule frontRight, SwerveModule backLeft, SwerveModule backRight, Gyro gyro, Clock clock) {
        mFrontLeft = frontLeft;
        mFrontRight = frontRight;
        mBackLeft = backLeft;
        mBackRight = backRight;
        // TODO: Extract odometry
        mGyro = gyro;
        mKinematics = new SwerveDriveKinematics(frontLeft.getLocation(), frontRight.getLocation(), backLeft.getLocation(), backRight.getLocation());
        mOdometry = new SwerveDriveOdometry(mKinematics, mGyro.getRotation2d());
        modules = new ModuleRunner<>(clock);
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
        var desiredSpeed = new SwerveSpeed(x, y, rotation);
        _currentSpeed = modules.run(_currentSpeed, desiredSpeed);
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

    @Override
    public void setModules(IModule<SwerveSpeed>... modules) {
        this.modules.setModules(modules);
    }

    @Override
    public void addModule(IModule<SwerveSpeed> module) {
        modules.addModule(module);
    }

    @Override
    public void addModule(int index, IModule<SwerveSpeed> module) {
        modules.addModule(index, module);
    }

    @Override
    public void removeModule(IModule<SwerveSpeed> module) {
        modules.removeModule(module);
    }

    @Override
    public List<IModule<SwerveSpeed>> getInstalledModules() {
        return modules.getInstalledModules();
    }

    @Override
    public <K extends IModule<SwerveSpeed>> K getInstalledModule(Class<K> cls) {
        return modules.getInstalledModule(cls);
    }

    @Override
    public void stop() {
        mFrontLeft.stop();
        mFrontRight.stop();
        mBackLeft.stop();
        mBackRight.stop();
        _currentSpeed = SwerveSpeed.STOP;
    }

    /**
     * If a drive method was not called within the reset threshold, all modules will be reset on the next invocation.
     *
     * @param resetThreshold The reset threshold in seconds.
     */
    public void setInactiveResetSeconds(double resetThreshold) {
        modules.setInactiveResetSeconds(resetThreshold);
    }
}
