package com.thegongoliers.output.drivemodules;

import com.thegongoliers.math.MathExt;

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
     * Default constructor
     * @param forwardStrength the forward ramping strength from 0 to 1 (0 provides no constraints, 1 prevents drivetrain from accelerating)
     * @param turnStrength the turning ramping strength from 0 to 1 (0 provides no constraints, 1 prevents drivetrain from accelerating)
     */
    public PowerEfficiencyModule(double forwardStrength, double turnStrength){
        super();
        values.put(VALUE_FORWARD_STRENGTH, forwardStrength);
        values.put(VALUE_TURN_STRENGTH, turnStrength);
    }

    @Override
    public DriveValue run(DriveValue currentSpeed, DriveValue desiredSpeed) {
        double forwardRate = 1 - (double) getValue(VALUE_FORWARD_STRENGTH);
        double turnRate = 1 - (double) getValue(VALUE_TURN_STRENGTH);

        double lastSpeed = currentSpeed.getForwardSpeed();
        double lastTurnSpeed = currentSpeed.getTurnSpeed();

        double speed = desiredSpeed.getForwardSpeed();
        double turnSpeed = desiredSpeed.getTurnSpeed();

        speed = MathExt.rateLimit(forwardRate, speed, lastSpeed);
        turnSpeed = MathExt.rateLimit(turnRate, turnSpeed, lastTurnSpeed);

        return new DriveValue(speed, turnSpeed);
    }

    @Override
    public int getOrder() {
        return 0;
    }

}