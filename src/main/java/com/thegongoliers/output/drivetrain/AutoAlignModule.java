package com.thegongoliers.output.drivetrain;

import java.util.List;

import com.thegongoliers.annotations.Untested;
import com.thegongoliers.math.GMath;

import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * A drivetrain module which will drive to a specified distance
 */
@Untested
public class AutoAlignModule extends BaseDriveModule {

    /**
     * The gyro used to determine the rotation of the drivetrain
     * Type: edu.wpi.first.wpilibj.interfaces.Gyro
     */
    public static final String VALUE_GYRO = "gyro";

    /**
     * The rotation strength (higher values may become unstable, small values recommended. Values must be >= 0)
     * Type: double
     */
    public static final String VALUE_STRENGTH = "strength";

    /**
     * The relative setpoint angle to align to.
     * Type: double
     */
    public static final String VALUE_SETPOINT = "setpoint";

    /**
     * Determines whether the drivetrain should rotate to the setpoint
     * Type: Trigger
     */
    public static final String VALUE_TRIGGER = "trigger";

    private double lastAngle;
    private boolean lastTrigger;

    /**
     * Default constructor
     * @param gyro the gyro used to determine the rotation of the drivetrain
     * @param strength the align strength (higher values may become unstable, small values recommended. Values must be >= 0)
     * @param trigger the trigger which will align the drivetrain
     */
    public AutoAlignModule(Gyro gyro, double strength, Trigger trigger){ // TODO: maybe set threshold instead of trigger
        super();
        values.put(VALUE_GYRO, gyro);
        values.put(VALUE_STRENGTH, strength);
        values.put(VALUE_TRIGGER, trigger); // TODO: Use simple boolean here instead
        values.put(VALUE_SETPOINT, 0);

        lastAngle = gyro.getAngle();
        lastTrigger = false;
    }

    @Override
    public DriveValue run(DriveValue currentSpeed, DriveValue desiredSpeed, double deltaTime) {
        double strength = (double) getValue(VALUE_STRENGTH);
        Trigger trigger = (Trigger) getValue(VALUE_TRIGGER);
        Gyro gyro = (Gyro) getValue(VALUE_GYRO);
        boolean currentTrigger = trigger.get();

        double speed = desiredSpeed.getForwardSpeed();
        double turnSpeed = desiredSpeed.getTurnSpeed();

        if (!lastTrigger && currentTrigger){
            lastAngle = gyro.getAngle();
        }

        if (currentTrigger){
            double setpoint = (double) getValue(VALUE_SETPOINT) + lastAngle;
            turnSpeed = GMath.clamp(-strength * (gyro.getAngle() - setpoint), -1, 1); // TODO: Determine direction of speed
            speed = 0;
        }

        lastTrigger = currentTrigger;

        return new DriveValue(speed, turnSpeed);
    }

    @Override
    public String getName() {
        return "Auto Align";
    }
}