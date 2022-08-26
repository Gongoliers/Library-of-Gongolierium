package com.thegongoliers.output.drivetrain.swerve;

import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.time.RobotClock;
import com.thegongoliers.math.SwerveKinematics;
import com.thegongoliers.output.interfaces.Stoppable;
import com.thegongoliers.utils.IModular;
import com.thegongoliers.utils.IModule;
import com.thegongoliers.utils.ModuleRunner;
import com.thegongoliers.utils.Resettable;

import java.util.List;

public class SwerveDrivetrain implements IModular<SwerveSpeed>, Stoppable, Resettable {

    private final SwerveModule mFrontLeft;
    private final SwerveModule mFrontRight;
    private final SwerveModule mBackLeft;
    private final SwerveModule mBackRight;
    private final SwerveKinematics mKinematics;
    private final ModuleRunner<SwerveSpeed> modules;
    private SwerveSpeed _currentSpeed = SwerveSpeed.STOP;

    public SwerveDrivetrain(SwerveModule frontLeft, SwerveModule frontRight, SwerveModule backLeft, SwerveModule backRight) {
        this(frontLeft, frontRight, backLeft, backRight, 1, 180, new RobotClock());
    }

    public SwerveDrivetrain(SwerveModule frontLeft, SwerveModule frontRight, SwerveModule backLeft, SwerveModule backRight, double maxSpeed, double maxRotationSpeedDegrees) {
        this(frontLeft, frontRight, backLeft, backRight, maxSpeed, maxRotationSpeedDegrees, new RobotClock());
    }

    public SwerveDrivetrain(SwerveModule frontLeft, SwerveModule frontRight, SwerveModule backLeft, SwerveModule backRight, double maxSpeed, double maxRotationSpeedDegrees, Clock clock) {
        mFrontLeft = frontLeft;
        mFrontRight = frontRight;
        mBackLeft = backLeft;
        mBackRight = backRight;
        mKinematics = new SwerveKinematics(frontLeft, frontRight, backLeft, backRight, maxSpeed, maxRotationSpeedDegrees);
        modules = new ModuleRunner<>(clock);
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
        var speeds = mKinematics.calculate(_currentSpeed);
        mFrontLeft.set(speeds.getFrontLeft());
        mFrontRight.set(speeds.getFrontRight());
        mBackLeft.set(speeds.getBackLeft());
        mBackRight.set(speeds.getBackRight());
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

    @Override
    public void reset() {
        mFrontLeft.reset();
        mFrontRight.reset();
        mBackLeft.reset();
        mBackRight.reset();
    }
}
