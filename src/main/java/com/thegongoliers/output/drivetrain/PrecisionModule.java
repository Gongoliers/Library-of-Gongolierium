package com.thegongoliers.output.drivetrain;

import com.thegongoliers.math.GMath;

/**
 * A drivetrain module which will allow higher precision driving at lower speeds. 
 */
public class PrecisionModule extends BaseDriveModule {

    /**
     * The precision strength from 0 to 1. Higher values will result in more precision movements at low speeds (corresponds to input^(4 * strength))
     * Type: double
     */
    public static final String VALUE_STRENGTH = "strength";

    /**
     * The name of the module
     */
    public static final String NAME = "Precision";

    /**
     * Default constructor
     * @param strength The precision strength from 0 to 1. Higher values will result in more precision movements at low speeds (corresponds to input^(4 * strength))
     */
    public PrecisionModule(double strength){
        super();
        values.put(VALUE_STRENGTH, strength);
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
        double strength = 4 * GMath.clamp01((double) getValue(VALUE_STRENGTH));

        double left = desiredSpeed.getLeftSpeed();
        double right = desiredSpeed.getRightSpeed();

        left = GMath.signPreservingPower(left, strength);
        right = GMath.signPreservingPower(right, strength);

        return new DriveSpeed(left, right);
    }

    @Override
    public String getName() {
        return NAME;
    }

}