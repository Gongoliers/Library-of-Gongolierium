package com.thegongoliers.output.drivemodules;

import com.thegongoliers.math.GMath;

/**
 * A drivetrain module which will allow higher precision driving at lower speeds. 
 */
public class PrecisionModule extends BaseDriveModule {

    /**
     * The forward precision strength from 0 to 1. Higher values will result in more precision movements at low speeds (corresponds to input^(4 * strength))
     * Type: double
     */
    public static final String VALUE_FORWARD_STRENGTH = "forward_strength";

    /**
     * The turn precision strength from 0 to 1. Higher values will result in more precision movements at low speeds (corresponds to input^(4 * strength))
     * Type: double
     */
    public static final String VALUE_TURN_STRENGTH = "turn_strength";

    /**
     * Default constructor
     * @param forwardStrength The forward precision strength from 0 to 1. Higher values will result in more precision movements at low speeds (corresponds to input^(4 * strength))
     * @param turnStrength The turn precision strength from 0 to 1. Higher values will result in more precision movements at low speeds (corresponds to input^(4 * strength))
     */
    public PrecisionModule(double forwardStrength, double turnStrength){
        super();
        values.put(VALUE_FORWARD_STRENGTH, forwardStrength);
        values.put(VALUE_TURN_STRENGTH, turnStrength);
    }

    @Override
    public DriveValue run(DriveValue currentSpeed, DriveValue desiredSpeed) {
        double forwardStrength = 4 * GMath.clamp01((double) getValue(VALUE_FORWARD_STRENGTH));
        double turnStrength = 4 * GMath.clamp01((double) getValue(VALUE_TURN_STRENGTH));

        double speed = desiredSpeed.getForwardSpeed();
        double turnSpeed = desiredSpeed.getTurnSpeed();

        speed = GMath.signPreservingPower(speed, forwardStrength);
        turnSpeed = GMath.signPreservingPower(turnSpeed, turnStrength);

        return new DriveValue(speed, turnSpeed);
    }

    @Override
    public int getOrder() {
        return 0;
    }

}