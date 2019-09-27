package com.thegongoliers.output.subsystems;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.thegongoliers.output.drivemodules.DriveModule;
import com.thegongoliers.output.drivemodules.DriveValue;
import com.thegongoliers.output.interfaces.Drivetrain;

/**
 * A wrapper class for a drivetrain which adds support for modules.
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
        drivetrain.tank(leftSpeed, rightSpeed);
    }

    public void addModule(DriveModule module){
        modules.add(module);
        modules.sort(Comparator.comparingInt(DriveModule::getOrder));
    }

    public void removeModule(DriveModule module){
        modules.remove(module);
    }

    
}