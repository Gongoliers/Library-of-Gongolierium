package com.thegongoliers.utils;

import com.thegongoliers.input.time.Clock;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class ModuleRunner<T> implements Resettable, IModular<T> {

    private final Clock mClock;
    private List<IModule<T>> modules;
    private double lastTime;
    private double resetThreshold = Double.POSITIVE_INFINITY;

    public ModuleRunner(Clock clock) {
        mClock = clock;
        lastTime = mClock.getTime();
        modules = new ArrayList<>();
    }

    public T run(T current, T desired) {
        double time = mClock.getTime();
        double dt = time - lastTime;

        if (dt >= resetThreshold) {
            reset();
        }

        var overrides = modules.stream().filter(IModule::overridesUser).collect(Collectors.toList());
        IModule<T> override = null;
        if (!overrides.isEmpty()) {
            override = overrides.get(overrides.size() - 1);
        }

        if (override != null) {
            desired = override.run(current, desired, dt);
        }

        for (IModule<T> module : modules) {
            if (override == module) {
                // The override has been seen, and its value is already the desired value, so let everything else run
                override = null;
                continue;
            }

            if (override == null) {
                desired = module.run(current, desired, dt);
            } else {
                // Reset the modules not being run
                resetModule(module);
            }
        }

        lastTime = time;
        return desired;
    }

    @Override
    public void reset() {
        modules.forEach(this::resetModule);
    }

    /**
     * Reset a module
     *
     * @param module the module to reset
     */
    private void resetModule(IModule<T> module) {
        if (module instanceof Resettable) {
            ((Resettable) module).reset();
        }
    }

    /**
     * Installs modules
     *
     * @param modules the modules
     */
    @SafeVarargs
    @Override
    public final void setModules(IModule<T>... modules) {
        this.modules = new ArrayList<>(List.of(modules));
    }

    /**
     * Add a module.
     * Note: you can add a module more than once, though this may produce undesired behavior
     *
     * @param module the module to add
     */
    @Override
    public void addModule(IModule<T> module) {
        if (module == null) return;
        modules.add(module);
    }

    /**
     * Add a module at a specific index.
     *
     * @param index  The index to add the module at
     * @param module The module to add
     */
    @Override
    public void addModule(int index, IModule<T> module) {
        if (module == null) return;
        modules.add(index, module);
    }

    /**
     * Remove a module
     *
     * @param module the module to remove
     */
    @Override
    public void removeModule(IModule<T> module) {
        modules.remove(module);
    }

    @Override
    public List<IModule<T>> getInstalledModules() {
        return modules;
    }

    /**
     * Gets the first installed module of the given type
     *
     * @param cls the class of the module to find
     * @return the first module of the given type, or null if it was not found
     */
    @SuppressWarnings("unchecked")
    @Override
    public <K extends IModule<T>> K getInstalledModule(Class<K> cls) {
        for (IModule<T> module : modules) {
            if (module.getClass() == cls) {
                return (K) module;
            }
        }
        return null;
    }

    /**
     * If the modules were not run within the reset threshold, all modules will be reset on the next invocation.
     *
     * @param resetThreshold The reset threshold in seconds.
     */
    public void setInactiveResetSeconds(double resetThreshold) {
        this.resetThreshold = resetThreshold;
    }

}
