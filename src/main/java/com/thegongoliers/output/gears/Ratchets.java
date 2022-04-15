package com.thegongoliers.output.gears;

import com.thegongoliers.output.interfaces.Piston;

public class Ratchets {

    private Ratchets() {
    }

    public static Ratchet am4426(Piston piston, boolean lockForward, double engageTime) {
        if (lockForward) {
            return new PneumaticTwoStateRatchet(piston, RatchetState.Neutral, RatchetState.LockForward, engageTime);
        } else {
            return new PneumaticTwoStateRatchet(piston, RatchetState.Neutral, RatchetState.LockReverse, engageTime);
        }
    }

    public static Ratchet am4426(Piston piston, boolean lockForward) {
        return am4426(piston, lockForward, 0.4);
    }

}
