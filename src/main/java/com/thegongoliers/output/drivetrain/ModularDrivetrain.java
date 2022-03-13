package com.thegongoliers.output.drivetrain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.thegongoliers.hardware.Hardware;
import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.time.RobotClock;
import com.thegongoliers.output.interfaces.Drivetrain;

import com.thegongoliers.utils.Resettable;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * A wrapper class for a drivetrain which adds support for drive modules. Does not apply drive modules during tank driving.
 */
public class ModularDrivetrain implements Drivetrain, Resettable {

    private Drivetrain drivetrain;
    private List<DriveModule> modules;
    private DriveSpeed currentSpeed;
    private Clock clock;
    private double lastTime;
    private double resetThreshold = Double.POSITIVE_INFINITY;

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
        modules = new ArrayList<>();
        currentSpeed = DriveSpeed.STOP;
        this.clock = clock;
        lastTime = clock.getTime();
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
        double time = clock.getTime();
        double dt = time - lastTime;

        if (dt >= resetThreshold){
            reset();
        }

        var overrides = modules.stream().filter(DriveModule::overridesUser).collect(Collectors.toList());
        DriveModule override = null;
        if (!overrides.isEmpty()) {
            override = overrides.get(overrides.size() - 1);
        }

        if (override != null) {
            desiredSpeed = override.run(currentSpeed, desiredSpeed, dt);
        }

        for (DriveModule module : modules) {
            if (override == module) {
                // The override has been seen, and its speed is already desired speed, so let everything else run
                override = null;
                continue;
            }

            if (override == null) {
                desiredSpeed = module.run(currentSpeed, desiredSpeed, dt);
            } else {
                // Reset the modules not being run
                resetModule(module);
            }
        }

        currentSpeed = desiredSpeed;
        lastTime = time;
        drivetrain.tank(currentSpeed.getLeftSpeed(), currentSpeed.getRightSpeed());
    }

    /**
     * Installs modules into the drivetrain
     *
     * @param modules the modules
     */
    public void setModules(DriveModule... modules) {
        this.modules = new ArrayList<>(List.of(modules));
    }

    /**
     * Add a module to the drivetrain.
     * Note: you can add a module more than once, though this may produce undesired behavior
     *
     * @param module the module to add
     */
    public void addModule(DriveModule module) {
        if (module == null) return;
        modules.add(module);
    }

    /**
     * Remove a module from the drivetrain
     *
     * @param module the module to remove
     */
    public void removeModule(DriveModule module) {
        modules.remove(module);
    }

    /**
     * Reset a module
     * @param module the module to reset
     */
    public void resetModule(DriveModule module){
        if (module instanceof Resettable){
            ((Resettable)module).reset();
        }
    }

    /**
     * Get all the installed modules
     *
     * @return the modules
     */
    public List<DriveModule> getInstalledModules() {
        return modules;
    }

    /**
     * Gets the first installed module of the given type
     *
     * @param cls the class of the module to find
     * @return the first module of the given type, or null if it was not found
     */
    @SuppressWarnings("unchecked")
    public <T> T getInstalledModule(Class<T> cls) {
        for (DriveModule module : modules) {
            if (module.getClass() == cls) {
                return (T) module;
            }
        }
        return null;
    }


    @Override
    public void reset() {
        modules.forEach(this::resetModule);
    }

    /**
     * If a drive method was not called within the reset threshold, all modules will be reset on the next invocation.
     * @param resetThreshold The reset threshold in seconds.
     */
    public void setInactiveResetSeconds(double resetThreshold){
        this.resetThreshold = resetThreshold;
    }
}