package com.thegongoliers.output.subsystems;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.thegongoliers.output.drivemodules.DriveModule;
import com.thegongoliers.output.drivemodules.DriveValue;
import com.thegongoliers.output.interfaces.Drivetrain;

/**
 * A wrapper class for a drivetrain which adds support for drive modules. Does not apply drive modules during tank driving.
 */
public class ModularDrivetrain implements Drivetrain {

    private Drivetrain drivetrain;
    private List<DriveModule> modules;
    private DriveValue currentSpeed;

    /**
     * Constructor
     * @param drivetrain the drivetrain
     */
    public ModularDrivetrain(Drivetrain drivetrain){
        this.drivetrain = drivetrain;
        modules = new ArrayList<>();
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
        // TODO: Add module support during tank mode
        drivetrain.tank(leftSpeed, rightSpeed);
    }

    /**
     * Add a module to the drivetrain, ordered by the DriveModule::getOrder property from lowest to highest. 
     * Note: you can add a module more than once, though this may produce undesired behavior
     * @param module the module to add
     */
    public void addModule(DriveModule module){
        if (module == null) return;
        modules.add(module);
        modules.sort(Comparator.comparingInt(DriveModule::getOrder));
    }

    /**
     * Remove a module from the drivetrain
     * @param module the module to remove
     */
    public void removeModule(DriveModule module){
        modules.remove(module);
    }

    
}