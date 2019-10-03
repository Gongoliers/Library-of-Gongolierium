package com.thegongoliers.output.drivetrain;

import java.util.ArrayList;
import java.util.List;

import com.thegongoliers.hardware.Hardware;
import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.time.RobotClock;
import com.thegongoliers.output.interfaces.Drivetrain;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * A wrapper class for a drivetrain which adds support for drive modules. Does not apply drive modules during tank driving.
 */
public class ModularDrivetrain implements Drivetrain {

    private Drivetrain drivetrain;
    private List<DriveModule> modules;
    private DriveValue currentSpeed;
    private Clock clock;
    private double lastTime;

    /**
     * Default constructor
     * @param drivetrain the drivetrain
     */
    public ModularDrivetrain(Drivetrain drivetrain){
        this(drivetrain, new RobotClock());
    }

    /**
     * Constructor (for testing)
     * @param drivetrain the drivetrain
     * @param clock the clock to use to calculate delta time
     */
    public ModularDrivetrain(Drivetrain drivetrain, Clock clock){
        this.drivetrain = drivetrain;
        modules = new ArrayList<>();
        currentSpeed = new DriveValue(0, 0);
        this.clock = clock;
        lastTime = clock.getTime();
    }

    /**
     * Create a modular drivetrain from a differential drive
     * @param differentialDrive the differential drive
     * @return the drivetrain
     */
    public static ModularDrivetrain from(DifferentialDrive differentialDrive){
        return new ModularDrivetrain(Hardware.createDrivetrain(differentialDrive));
    }


    @Override
    public void stop() {
        drivetrain.stop();
    }

    @Override
    public void arcade(double speed, double turn) {
        DriveValue desiredSpeed = new DriveValue(speed, turn);
        double time = clock.getTime();
        double dt = time - lastTime;

        for(DriveModule module : modules){
            desiredSpeed = module.run(currentSpeed, desiredSpeed, dt);
        }
        
        currentSpeed = desiredSpeed;
        lastTime = time;
        drivetrain.arcade(currentSpeed.getForwardSpeed(), currentSpeed.getTurnSpeed());
    }

    @Override
    public void tank(double leftSpeed, double rightSpeed) {
        drivetrain.tank(leftSpeed, rightSpeed);
    }

    /**
     * Installs modules into the drivetrain
     * @param modules the modules
     */
    public void setModules(DriveModule... modules){
        this.modules = new ArrayList<>(List.of(modules));
    }

    /**
     * Add a module to the drivetrain.
     * Note: you can add a module more than once, though this may produce undesired behavior
     * @param module the module to add
     */
    public void addModule(DriveModule module){
        if (module == null) return;
        modules.add(module);
    }

    /**
     * Remove a module from the drivetrain
     * @param module the module to remove
     */
    public void removeModule(DriveModule module){
        modules.remove(module);
    }

    /**
     * Get all the installed modules
     * @return the modules
     */
    public List<DriveModule> getInstalledModules(){
        return modules;
    }

    
}