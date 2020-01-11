package com.thegongoliers.output.drivetrain;

import com.thegongoliers.math.GMath;

/**
 * A drivetrain module which will allow higher precision driving at lower speeds. 
 */
public class PrecisionModule implements DriveModule {

    /**
     * The name of the module
     */
    public static final String NAME = "Precision";

    private double mStrength;

    /**
     * Default constructor
     * @param strength The precision strength from 0 to 1. Higher values will result in more precision movements at low speeds (corresponds to input^(4 * strength))
     */
    public PrecisionModule(double strength){
        super();
        mStrength = strength;
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
        double exponent = calculateExponent();

        double left = desiredSpeed.getLeftSpeed();
        double right = desiredSpeed.getRightSpeed();

        left = GMath.signPreservingPower(left, exponent);
        right = GMath.signPreservingPower(right, exponent);

        return new DriveSpeed(left, right);
    }

    public void setStrength(double strength){
        mStrength = strength;
    }

    private double calculateExponent() {
        return 4 * GMath.clamp01(mStrength);
    }

    @Override
    public String getName() {
        return NAME;
    }

}