package com.thegongoliers.output.drivetrain;

import com.thegongoliers.math.GMath;

/**
 * A drivetrain module which will constrain the maximum speed of the robot. 
 */
public class SpeedConstraintModule extends BaseDriveModule {

    /**
     * The maximum speed
     * Type: double
     */
    public static final String VALUE_MAX_SPEED = "max_speed";

    /**
     * True if the speeds should be scaled to the clamped range, or false otherwise
     * Type: boolean
     */
    public static final String VALUE_SCALE_SPEEDS = "scale_speeds";

    /**
     * The name of the module
     */
    public static final String NAME = "Speed Constraint";

    /**
     * Default constructor
     * @param maxSpeed The maximum speed
     * @param scaleSpeeds True if the speeds should be scaled to the clamped range, or false otherwise
     */
    public SpeedConstraintModule(double maxSpeed, boolean scaleSpeeds){
        super();
        values.put(VALUE_MAX_SPEED, maxSpeed);
        values.put(VALUE_SCALE_SPEEDS, scaleSpeeds);
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
        double maxSpeed = (double) getValue(VALUE_MAX_SPEED);
        boolean scaleSpeed = (boolean) getValue(VALUE_SCALE_SPEEDS);

        double left = desiredSpeed.getLeftSpeed();
        double right = desiredSpeed.getRightSpeed();

        if (scaleSpeed){
            left *= maxSpeed;
            right *= maxSpeed;
        } else {
            left = GMath.clamp(left, -maxSpeed, maxSpeed);
            right = GMath.clamp(right, -maxSpeed, maxSpeed);
        }

        return new DriveSpeed(left, right);
    }

    @Override
    public String getName() {
        return NAME;
    }

}