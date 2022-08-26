package com.thegongoliers.output.drivetrain;

import com.thegongoliers.hardware.Hardware;
import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.time.RobotClock;
import com.thegongoliers.output.interfaces.Drivetrain;
import com.thegongoliers.utils.IModular;
import com.thegongoliers.utils.IModule;
import com.thegongoliers.utils.ModuleRunner;
import com.thegongoliers.utils.Resettable;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import java.util.List;

/**
 * A wrapper class for a drivetrain which adds support for drive modules. Does not apply drive modules during tank driving.
 */
public class ModularDrivetrain implements Drivetrain, Resettable, IModular<DriveSpeed> {

    private Drivetrain drivetrain;
    private ModuleRunner<DriveSpeed> modules;
    private DriveSpeed currentSpeed;

    /**
     * Default constructor
     *
     * @param drivetrain the drivetrain
     */
    public ModularDrivetrain(Drivetrain drivetrain) {
        this(drivetrain, new RobotClock());
    }

    /**
     * Constructor (for testing)
     *
     * @param drivetrain the drivetrain
     * @param clock      the clock to use to calculate delta time
     */
    public ModularDrivetrain(Drivetrain drivetrain, Clock clock) {
        this.drivetrain = drivetrain;
        modules = new ModuleRunner<>(clock);
        currentSpeed = DriveSpeed.STOP;
    }

    /**
     * Create a modular drivetrain from a differential drive
     *
     * @param differentialDrive the differential drive
     * @return the drivetrain
     */
    public static ModularDrivetrain from(DifferentialDrive differentialDrive) {
        return new ModularDrivetrain(Hardware.createDrivetrain(differentialDrive));
    }


    @Override
    public void stop() {
        currentSpeed = DriveSpeed.STOP;
        drivetrain.stop();
    }

    @Override
    public void arcade(double speed, double turn) {
        DriveSpeed desiredSpeed = DriveSpeed.fromArcade(speed, turn);
        tank(desiredSpeed.getLeftSpeed(), desiredSpeed.getRightSpeed());
    }

    @Override
    public void tank(double leftSpeed, double rightSpeed) {
        DriveSpeed desiredSpeed = new DriveSpeed(leftSpeed, rightSpeed);
        currentSpeed = modules.run(currentSpeed, desiredSpeed);
        drivetrain.tank(currentSpeed.getLeftSpeed(), currentSpeed.getRightSpeed());
    }

    @SafeVarargs
    @Override
    public final void setModules(IModule<DriveSpeed>... modules) {
        this.modules.setModules(modules);
    }

    @Override
    public void addModule(IModule<DriveSpeed> module) {
        modules.addModule(module);
    }

    @Override
    public void addModule(int index, IModule<DriveSpeed> module) {
        modules.addModule(index, module);
    }

    @Override
    public void removeModule(IModule<DriveSpeed> module) {
        modules.removeModule(module);
    }

    @Override
    public List<IModule<DriveSpeed>> getInstalledModules() {
        return modules.getInstalledModules();
    }

    @Override
    public <K> K getInstalledModule(Class<K> cls) {
        return modules.getInstalledModule(cls);
    }

    @Override
    public void reset() {
        modules.reset();
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