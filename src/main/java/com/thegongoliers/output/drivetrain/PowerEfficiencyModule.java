package com.thegongoliers.output.drivetrain;

import com.thegongoliers.math.GMath;

/**
 * A drivetrain module which will force the drivetrain to accelerate slower. 
 */
public class PowerEfficiencyModule extends BaseDriveModule {

    /**
     * The ramping strength from 0 to 1 (0 provides no constraints, 1 prevents drivetrain from accelerating)
     * Type: double
     */
    public static final String VALUE_STRENGTH = "strength";

    /**
     * The name of the module
     */
    public static final String NAME = "Power Efficiency";

    /**
     * Default constructor
     * @param strength the ramping strength from 0 to 1 (represents time in seconds from 0 to full speed)
     */
    public PowerEfficiencyModule(double strength){
        super();
        values.put(VALUE_STRENGTH, strength);
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
        double forwardStrength = (double) getValue(VALUE_STRENGTH);

        double forwardRate = forwardStrength == 0 ? 1 : deltaTime / forwardStrength;

        double lastLeft = currentSpeed.getLeftSpeed();
        double lastRight = currentSpeed.getRightSpeed();

        double leftSpeed = desiredSpeed.getLeftSpeed();
        double rightSpeed = desiredSpeed.getRightSpeed();

        leftSpeed = GMath.rateLimit(forwardRate, leftSpeed, lastLeft);
        rightSpeed = GMath.rateLimit(forwardRate, rightSpeed, lastRight);

        return new DriveSpeed(leftSpeed, rightSpeed);
    }

    @Override
    public String getName() {
        return NAME;
    }

}