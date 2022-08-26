package com.thegongoliers.output.motors;

import com.thegongoliers.annotations.Untested;
import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.time.RobotClock;
import com.thegongoliers.output.actuators.BaseMotorControllerDecorator;
import com.thegongoliers.utils.IModular;
import com.thegongoliers.utils.IModule;
import com.thegongoliers.utils.ModuleRunner;
import com.thegongoliers.utils.Resettable;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

import java.util.List;
import java.util.function.DoubleConsumer;

@Untested
public class ModularMotor extends BaseMotorControllerDecorator implements DoubleConsumer, Resettable, IModular<Double> {

    private ModuleRunner<Double> modules;

    public ModularMotor(MotorController speedController, Clock clock) {
        super(speedController);
        modules = new ModuleRunner<>(clock);
    }

    public ModularMotor(MotorController speedController) {
        this(speedController, new RobotClock());
    }

    public static ModularMotor from(MotorController controller) {
        return new ModularMotor(controller);
    }

    @Override
    public void set(double speed) {
        speed = modules.run(get(), speed);
        super.set(speed);
    }

    @Override
    public void setModules(IModule<Double>... modules) {
        this.modules.setModules(modules);
    }

    @Override
    public void addModule(IModule<Double> module) {
        modules.addModule(module);
    }

    @Override
    public void addModule(int index, IModule<Double> module) {
        modules.addModule(index, module);
    }

    @Override
    public void removeModule(IModule<Double> module) {
        modules.removeModule(module);
    }

    @Override
    public List<IModule<Double>> getInstalledModules() {
        return modules.getInstalledModules();
    }

    @Override
    public <K> K getInstalledModule(Class<K> cls) {
        return modules.getInstalledModule(cls);
    }

    // Reset
    @Override
    public void reset() {
        modules.reset();
    }

    @Override
    public void accept(double speed) {
        set(speed);
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
