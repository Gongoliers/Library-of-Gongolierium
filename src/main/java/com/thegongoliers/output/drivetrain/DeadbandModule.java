package com.thegongoliers.output.drivetrain;

import com.thegongoliers.math.GMath;

/**
 * A drivetrain module which will stop the drivetrain at low input values
 */
public class DeadbandModule extends BaseDriveModule {

    /**
     * The forward threshold from 0 to 1 to treat as 0
     * Type: double
     */
    public static final String VALUE_FORWARD_THRESHOLD = "forward_threshold";

    /**
     * The turning threshold from 0 to 1 to treat as 0
     * Type: double
     */
    public static final String VALUE_TURN_THRESHOLD = "turn_threshold";


    /**
     * Default constructor
     * @param forwardThreshold the forward threshold from 0 to 1 to treat as 0
     * @param turnThreshold the turning threshold from 0 to 1 to treat as 0
     */
    public DeadbandModule(double forwardThreshold, double turnThreshold){
        super();
        values.put(VALUE_FORWARD_THRESHOLD, forwardThreshold);
        values.put(VALUE_TURN_THRESHOLD, turnThreshold);
    }

    @Override
    public DriveValue run(DriveValue currentSpeed, DriveValue desiredSpeed, double deltaTime) {
        double forwardThreshold = (double) getValue(VALUE_FORWARD_THRESHOLD);
        double turnThreshold = (double) getValue(VALUE_TURN_THRESHOLD);

        double speed = desiredSpeed.getForwardSpeed();
        double turnSpeed = desiredSpeed.getTurnSpeed();

        speed = GMath.deadband(speed, forwardThreshold);
        turnSpeed = GMath.deadband(turnSpeed, turnThreshold);

        return new DriveValue(speed, turnSpeed);
    }

    @Override
    public String getName() {
        return "Deadband";
    }

}