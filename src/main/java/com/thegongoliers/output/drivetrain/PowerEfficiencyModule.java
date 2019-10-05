package com.thegongoliers.output.drivetrain;

import com.thegongoliers.math.GMath;

/**
 * A drivetrain module which will force the drivetrain to accelerate slower. 
 */
public class PowerEfficiencyModule extends BaseDriveModule {

    /**
     * The forward ramping strength from 0 to 1 (0 provides no constraints, 1 prevents drivetrain from accelerating)
     * Type: double
     */
    public static final String VALUE_FORWARD_STRENGTH = "forward_strength";

    /**
     * The turning ramping strength from 0 to 1 (0 provides no constraints, 1 prevents drivetrain from accelerating).
     * Type: double
     */
    public static final String VALUE_TURN_STRENGTH = "turn_strength";

    /**
     * The name of the module
     */
    public static final String NAME = "Power Efficiency";

    /**
     * Default constructor
     * @param forwardStrength the forward ramping strength from 0 to 1 (represents time in seconds from 0 to full speed)
     * @param turnStrength the turning ramping strength from 0 to 1 (represents time in seconds from 0 to full turn speed)
     */
    public PowerEfficiencyModule(double forwardStrength, double turnStrength){
        super();
        values.put(VALUE_FORWARD_STRENGTH, forwardStrength);
        values.put(VALUE_TURN_STRENGTH, turnStrength);
    }

    @Override
    public DriveValue run(DriveValue currentSpeed, DriveValue desiredSpeed, double deltaTime) {
        double forwardStrength = (double) getValue(VALUE_FORWARD_STRENGTH);
        double turnStrength = (double) getValue(VALUE_TURN_STRENGTH);

        double forwardRate = forwardStrength == 0 ? 1 : deltaTime / forwardStrength;
        double turnRate = turnStrength == 0 ? 1 : deltaTime / turnStrength;

        double lastSpeed = currentSpeed.getForwardSpeed();
        double lastTurnSpeed = currentSpeed.getTurnSpeed();

        double speed = desiredSpeed.getForwardSpeed();
        double turnSpeed = desiredSpeed.getTurnSpeed();

        speed = GMath.rateLimit(forwardRate, speed, lastSpeed);
        turnSpeed = GMath.rateLimit(turnRate, turnSpeed, lastTurnSpeed);

        return new DriveValue(speed, turnSpeed);
    }

    @Override
    public String getName() {
        return NAME;
    }

}