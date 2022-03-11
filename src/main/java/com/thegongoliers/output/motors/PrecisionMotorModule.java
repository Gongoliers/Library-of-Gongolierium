package com.thegongoliers.output.motors;

import com.thegongoliers.annotations.Untested;
import com.thegongoliers.math.GMath;

@Untested
public class PrecisionMotorModule implements MotorModule {

    private final double mStrength;

    public PrecisionMotorModule(double strength){
        mStrength = strength;
    }

    @Override
    public double run(double currentSpeed, double desiredSpeed, double deltaTime) {
        var exponent = 4 * GMath.clamp01(mStrength);
        return GMath.signPreservingPower(desiredSpeed, exponent);
    }
}
