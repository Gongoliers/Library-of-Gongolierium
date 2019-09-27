package com.thegongoliers.output.drivemodules;

import java.util.List;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * A drivetrain module which will lock the drivetrain in place while a trigger condition is met
 */
public class FortifyModule extends BaseDriveModule {

    public static final String VALUE_ENCODERS = "encoders";
    public static final String VALUE_STRENGTH = "strength";
    public static final String VALUE_TRIGGER = "trigger";

    private double lastDistance;
    private boolean lastTrigger;

    /**
     * Default constructor
     * @param gyro the gyroscope which determines the robot's heading
     * @param strength the stabilizing strength (higher values may become unstable, recommended ~0.02. Values must be >= 0)
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
    public DriveValue run(DriveValue currentSpeed, DriveValue desiredSpeed) {
        double strength = (double) getValue(VALUE_STRENGTH);
        Trigger trigger = (Trigger) getValue(VALUE_TRIGGER);
        boolean currentTrigger = trigger.get();

        double speed = desiredSpeed.getForwardSpeed();
        double turnSpeed = desiredSpeed.getTurnSpeed();

        if (!lastTrigger && currentTrigger){
            lastDistance = getDistance();
        }

        if (currentTrigger){
            speed = -strength * (getDistance() - lastDistance); // TODO: Verify direction
            turnSpeed = 0;
        }

        lastTrigger = currentTrigger;

        return new DriveValue(speed, turnSpeed);
    }

    @Override
    public int getOrder() {
        return 0;
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