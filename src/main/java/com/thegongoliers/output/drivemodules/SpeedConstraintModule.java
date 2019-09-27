package com.thegongoliers.output.drivemodules;

import com.thegongoliers.math.MathExt;

/**
 * A drivetrain module which will constrain the maximum speed of the robot. 
 */
public class SpeedConstraintModule extends BaseDriveModule {

    /**
     * The maximum forward speed
     * Type: double
     */
    public static final String VALUE_MAX_SPEED = "max_speed";

    /**
     * The maximum turn speed
     * Type: double
     */
    public static final String VALUE_MAX_TURN_SPEED = "max_turn_speed";

    /**
     * True if the speeds should be scaled to the clamped range, or false otherwise
     * Type: boolean
     */
    public static final String VALUE_SCALE_SPEEDS = "scale_speeds";

    /**
     * Default constructor
     * @param maxSpeed The maximum forward speed
     * @param maxTurnSpeed The maximum turn speed
     * @param scaleSpeeds True if the speeds should be scaled to the clamped range, or false otherwise
     */
    public SpeedConstraintModule(double maxSpeed, double maxTurnSpeed, boolean scaleSpeeds){
        super();
        values.put(VALUE_MAX_SPEED, maxSpeed);
        values.put(VALUE_MAX_TURN_SPEED, maxTurnSpeed);
        values.put(VALUE_SCALE_SPEEDS, scaleSpeeds);
    }

    @Override
    public DriveValue run(DriveValue currentSpeed, DriveValue desiredSpeed) {
        double maxSpeed = (double) getValue(VALUE_MAX_SPEED);
        double maxTurnSpeed = (double) getValue(VALUE_MAX_TURN_SPEED);
        boolean scaleSpeed = (boolean) getValue(VALUE_SCALE_SPEEDS);

        double speed = desiredSpeed.getForwardSpeed();
        double turnSpeed = desiredSpeed.getTurnSpeed();

        if (scaleSpeed){
            speed *= maxSpeed;
            turnSpeed *= maxTurnSpeed;
        } else {
            speed = MathExt.clamp(speed, -maxSpeed, maxSpeed);
            turnSpeed = MathExt.clamp(turnSpeed, -maxTurnSpeed, maxTurnSpeed);
        }

        return new DriveValue(speed, turnSpeed);
    }

    @Override
    public int getOrder() {
        return 10;
    }

}