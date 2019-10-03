package com.thegongoliers.output.drivetrain;

import java.util.List;

import com.thegongoliers.annotations.Untested;
import com.thegongoliers.math.GMath;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * A drivetrain module which will drive to a specified distance
 */
@Untested
public class AutoDriveModule extends BaseDriveModule {

    /**
     * The encoders that are used to determine the average distance traveled by the drivetrain
     * Type: java.util.List<edu.wpi.first.wpilibj.Encoder>
     */
    public static final String VALUE_ENCODERS = "encoders";

    /**
     * The drive strength (higher values may become unstable, small values recommended. Values must be >= 0)
     * Type: double
     */
    public static final String VALUE_STRENGTH = "strength";

    /**
     * The relative setpoint distance to travel.
     * Type: double
     */
    public static final String VALUE_SETPOINT = "setpoint";

    /**
     * Determines whether the drivetrain should move to the setpoint
     * Type: Trigger
     */
    public static final String VALUE_TRIGGER = "trigger";

    private double lastDistance;
    private boolean lastTrigger;

    /**
     * Default constructor
     * @param encoders the encoders that are used to determine the average distance traveled by the drivetrain
     * @param strength the drive strength (higher values may become unstable, small values recommended. Values must be >= 0)
     * @param trigger the trigger which will move to the setpoint
     */
    public AutoDriveModule(List<Encoder> encoders, double strength, Trigger trigger){
        super();
        values.put(VALUE_ENCODERS, encoders);
        values.put(VALUE_STRENGTH, strength);
        values.put(VALUE_TRIGGER, trigger);
        values.put(VALUE_SETPOINT, 0);

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
            double setpoint = (double) getValue(VALUE_SETPOINT) + lastDistance;
            speed = GMath.clamp(-strength * (getDistance() - setpoint), -1, 1); // TODO: Determine direction of speed
            turnSpeed = 0;
        }

        lastTrigger = currentTrigger;

        return new DriveValue(speed, turnSpeed);
    }

    @Override
    public String getName() {
        return "Auto Drive";
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