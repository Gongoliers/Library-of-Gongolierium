package com.thegongoliers.output.motors;

import com.thegongoliers.annotations.Untested;
import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.time.RobotClock;
import com.thegongoliers.output.actuators.BaseMotorControllerDecorator;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Untested
public class ModularMotor extends BaseMotorControllerDecorator {

    private final Clock mClock;
    private List<MotorModule> mModules;
    private double mLastTime;

    public ModularMotor(MotorController speedController, Clock clock) {
        super(speedController);
        mClock = clock;
        mLastTime = mClock.getTime();
    }

    public ModularMotor(MotorController speedController) {
        this(speedController, new RobotClock());
    }

    public static ModularMotor from(MotorController controller) {
        return new ModularMotor(controller);
    }

    @Override
    public void set(double speed) {
        double time = mClock.getTime();
        double dt = time - mLastTime;
        double current = get();
        double desired = speed;

        var overrides = mModules.stream().filter(MotorModule::overridesUser).collect(Collectors.toList());
        MotorModule override = null;
        if (!overrides.isEmpty()) {
            override = overrides.get(overrides.size() - 1);
        }

        if (override != null) {
            desired = override.run(current, desired, dt);
        }

        for (var module : mModules) {
            if (override == module) {
                // The override has been seen, and its speed is already desired speed, so let everything else run
                override = null;
                continue;
            }

            if (override == null) {
                desired = module.run(current, desired, dt);
            } else {
                // Run the module to ensure it stay updated, but don't update the desired speed since it should be overridden
                module.run(current, desired, dt);
            }
        }
        mLastTime = time;
        super.set(desired);
    }

    /**
     * Installs modules into the motor
     *
     * @param modules the modules
     */
    public void setModules(MotorModule... modules) {
        this.mModules = new ArrayList<>(List.of(modules));
    }

    /**
     * Add a module to the motor.
     * Note: you can add a module more than once, though this may produce undesired behavior
     *
     * @param module the module to add
     */
    public void addModule(MotorModule module) {
        if (module == null) return;
        mModules.add(module);
    }

    /**
     * Remove a module from the motor
     *
     * @param module the module to remove
     */
    public void removeModule(MotorModule module) {
        mModules.remove(module);
    }

    /**
     * Get all the installed modules
     *
     * @return the modules
     */
    public List<MotorModule> getInstalledModules() {
        return mModules;
    }

    /**
     * Gets the first installed module of the given type
     *
     * @param cls the class of the module to find
     * @return the first module of the given type, or null if it was not found
     */
    @SuppressWarnings("unchecked")
    public <T> T getInstalledModule(Class<T> cls) {
        for (var module : mModules) {
            if (module.getClass() == cls) {
                return (T) module;
            }
        }
        return null;
    }
}