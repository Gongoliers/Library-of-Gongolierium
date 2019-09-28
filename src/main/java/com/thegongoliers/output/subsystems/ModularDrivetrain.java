package com.thegongoliers.output.subsystems;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thegongoliers.output.drivemodules.DriveModule;
import com.thegongoliers.output.drivemodules.DriveValue;
import com.thegongoliers.output.interfaces.Drivetrain;

/**
 * A wrapper class for a drivetrain which adds support for drive modules. Does not apply drive modules during tank driving.
 */
public class ModularDrivetrain implements Drivetrain {

    private Drivetrain drivetrain;
    private List<DriveModule> modules;
    private Map<DriveModule, Integer> order; 
    private DriveValue currentSpeed;

    /**
     * Constructor
     * @param drivetrain the drivetrain
     */
    public ModularDrivetrain(Drivetrain drivetrain){
        this.drivetrain = drivetrain;
        modules = new ArrayList<>();
        order = new HashMap<>();
        currentSpeed = new DriveValue(0, 0);
    }


    @Override
    public void stop() {
        drivetrain.stop();
    }

    @Override
    public void arcade(double speed, double turn) {
        DriveValue desiredSpeed = new DriveValue(speed, turn);

        for(DriveModule module : modules){
            desiredSpeed = module.run(currentSpeed, desiredSpeed);
        }
        
        currentSpeed = desiredSpeed;
        drivetrain.arcade(currentSpeed.getForwardSpeed(), currentSpeed.getTurnSpeed());
    }

    @Override
    public void tank(double leftSpeed, double rightSpeed) {
        drivetrain.tank(leftSpeed, rightSpeed);
    }

    /**
     * Add a module to the drivetrain.
     * Note: you can add a module more than once, though this may produce undesired behavior
     * @param module the module to add
     * @param order the order to sort this module, lower order modules will be run before higher order modules
     */
    public void addModule(DriveModule module, int order){
        if (module == null) return;
        modules.add(module);
        this.order.put(module, order);
        modules.sort(Comparator.comparingInt(m -> this.order.get(m)));
    }

    /**
     * Add a module to the drivetrain, using the drivemodule's default order. 
     * Note: you can add a module more than once, though this may produce undesired behavior
     * @param module the module to add
     */
    public void addModule(DriveModule module){
        addModule(module, module.getOrder());
    }

    /**
     * Remove a module from the drivetrain
     * @param module the module to remove
     */
    public void removeModule(DriveModule module){
        modules.remove(module);
        order.remove(module);
    }

    public List<DriveModule> getInstalledModules(){
        return modules;
    }

    
}