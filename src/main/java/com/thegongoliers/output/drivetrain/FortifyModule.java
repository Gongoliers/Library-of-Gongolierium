package com.thegongoliers.output.drivetrain;

import java.util.List;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * A drivetrain module which will lock the drivetrain in place while a trigger condition is met
 */
public class FortifyModule extends BaseDriveModule {

    /**
     * The encoders that are used to determine the average distance traveled by the drivetrain
     * Type: java.util.List<edu.wpi.first.wpilibj.Encoder>
     */
    public static final String VALUE_ENCODERS = "encoders";

    /**
     * The fortify strength (higher values may become unstable, small values recommended. Values must be >= 0)
     * Type: double
     */
    public static final String VALUE_STRENGTH = "strength";

    /**
     * The trigger which will lock the drivetrain in place
     * Type: edu.wpi.first.wpilibj.buttons.Trigger
     */
    public static final String VALUE_TRIGGER = "trigger";

    /**
     * The name of the module
     */
    public static final String NAME = "Fortify";

    private double lastDistance;
    private boolean lastTrigger;

    /**
     * Default constructor
     * @param encoders the encoders that are used to determine the average distance traveled by the drivetrain
     * @param strength the fortify strength (higher values may become unstable, small values recommended. Values must be >= 0)
     * @param trigger the trigger which will lock the drivetrain in place
     */
    public FortifyModule(List<Encoder> encoders, double strength, Trigger trigger){
        super();
        values.put(VALUE_ENCODERS, encoders);
        values.put(VALUE_STRENGTH, strength);
        values.put(VALUE_TRIGGER, trigger);

        lastDistance = getDistance();
        lastTrigger = false;
    }

    @Override
    public DriveValue run(DriveValue currentSpeed, DriveValue desiredSpeed, double deltaTime) {
        double strength = (double) getValue(VALUE_STRENGTH);
        Trigger trigger = (Trigger) getValue(VALUE_TRIGGER);
        boolean currentTrigger = trigger.get();

        double speed = desiredSpeed.getForwardSpeed();
        double turnSpeed = desiredSpeed.getTurnSpeed();

        if (!lastTrigger && currentTrigger){
            lastDistance = getDistance();
        }

        if (currentTrigger){
            speed = strength * (lastDistance - getDistance());
            turnSpeed = 0;
        }

        lastTrigger = currentTrigger;

        return new DriveValue(speed, turnSpeed);
    }

    @Override
    public String getName() {
        return NAME;
    }

    private double getDistance(){
        List<Encoder> encoders = (List<Encoder>) getValue(VALUE_ENCODERS);
        double sum = 0;
        for (Encoder encoder : encoders) {
            sum += encoder.getDistance();
        }
        return sum / encoders.size();
    }

}